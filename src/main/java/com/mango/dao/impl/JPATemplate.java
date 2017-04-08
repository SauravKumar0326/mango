package com.mango.dao.impl;

import static com.mango.utils.Constants.ERROR_SORT_BY_COLUMN;
import static com.mango.utils.Constants.SCIM_PARSING_FAILED;
import static com.mango.utils.Constants.SORTING_ERROR;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mango.entities.Sortable;
import com.mango.enums.OrderByType;
import com.mango.exception.BadRequestException;
import com.mango.exception.DAOException;
import com.mango.exception.MangoException;
import com.mango.exception.ObjectNotFoundException;
import com.mango.utils.SCIMFilter;

public abstract class JPATemplate<T> {
	private static final Logger log = LoggerFactory.getLogger(JPATemplate.class);
	@PersistenceContext
	protected EntityManager em;

	protected Class<T> entityClass;

	protected JPATemplate(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	abstract protected Predicate getTenantPredicate(long companyId, CriteriaBuilder cb, Root<T> root);

	protected T save(T entity) {
		em.persist(entity);
		return entity;
	}

	public void delete(T entity) throws DAOException {
		em.remove(entity);
	}

	public T find(Object entityID) throws DAOException {
		T entity = em.find(entityClass, entityID);
		if (entity == null) {
			throw new ObjectNotFoundException();
		}
		return entity;
	}

	public <S> S find(Object entityID, Class<S> S) throws DAOException {
		S entity = em.find(S, entityID);
		if (entity == null) {
			throw new ObjectNotFoundException();
		}
		return entity;
	}

	protected T find(long entityID) throws DAOException {
		T entity = em.find(entityClass, entityID);
		if (entity == null) {
			throw new ObjectNotFoundException();
		}
		return entity;
	}

	protected List<T> findAll() {
		List<T> result = new ArrayList<>();
		try {
			CriteriaQuery<T> cq = em.getCriteriaBuilder().createQuery(entityClass);
			cq.select(cq.from(entityClass));
			result = em.createQuery(cq).getResultList();
		} catch (NoResultException | NonUniqueResultException e) {
			log.error("Found zero or more than one records for the given parameters. : " + e);
		}
		return result;
	}

	protected T findOne(String namedQuery, Map<String, Object> parameters) throws DAOException {
		T result = null;
		try {
			if (log.isDebugEnabled()) {
				log.debug("Find one record for {} with query {}  and params {} is started.", namedQuery, parameters);
			}
			TypedQuery<T> query = em.createNamedQuery(namedQuery, entityClass);

			// Method that will populate parameters if they are passed not null
			// and empty
			if (parameters != null && !parameters.isEmpty()) {
				populateQueryParameters(query, parameters);
			}

			result = (T) query.getSingleResult();
			if (log.isDebugEnabled()) {
				log.debug("Find one finished.");
			}
		} catch (NoResultException | NonUniqueResultException e) {
			if (e instanceof NoResultException) {
				throw new ObjectNotFoundException();
			}
			log.error("Found zero or more than one records for the given parameters. : " + e);
			throw e;
		}
		return result;
	}

	protected List<T> findAll(String namedQuery, Map<String, Object> parameters, int start, int count) {
		if (log.isDebugEnabled()) {
			log.debug("Find records for {} with query {}  and params {} is started.", namedQuery, parameters);
		}
		List<T> result = null;
		try {
			TypedQuery<T> query = em.createNamedQuery(namedQuery, entityClass);
			// Method that will populate parameters if they are passed not null
			// and empty
			if (parameters != null && !parameters.isEmpty()) {
				populateQueryParameters(query, parameters);
			}
			// Pagination if desired.
			if (start != 0 || count != 0) {
				query.setFirstResult(start);
				query.setMaxResults(count);
			}
			result = query.getResultList();
		} catch (NoResultException | NonUniqueResultException e) {
			log.error("Found zero or more than one records for the given parameters. : " + e);
		}
		if (log.isDebugEnabled()) {
			log.debug("Find All finished with size {} ", result.size());
		}
		return result;
	}

	protected List<T> findAll(String namedQuery, Map<String, Object> parameters) {
		return findAll(namedQuery, parameters, 0, 0);
	}

	protected <S> List<S> findAll(String namedQuery, Map<String, Object> parameters, Class<S> clazz)
			throws DAOException {
		if (log.isDebugEnabled()) {
			log.debug("Find records for {} with query {} with params {} ", namedQuery, parameters);
		}
		List<S> result = null;
		try {
			TypedQuery<S> query = em.createNamedQuery(namedQuery, clazz);
			// Method that will populate parameters if they are passed not null
			// and empty
			if (parameters != null && !parameters.isEmpty()) {
				populateQueryParameters(query, parameters);
			}

			result = query.getResultList();
		} catch (NoResultException | NonUniqueResultException e) {
			log.error("Found zero or more than one records for the given parameters. : " + e);
		}
		if (log.isDebugEnabled()) {
			log.debug("Find All finished with size {} ", result.size());
		}
		return result;
	}

	private void populateQueryParameters(Query query, Map<String, Object> parameters) {
		for (Entry<String, Object> entry : parameters.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}

	protected int countAll(String namedQuery, Map<String, Object> parameters) throws DAOException {
		int result = 0;
		try {
			if (log.isDebugEnabled()) {
				log.debug("Count All for {} with query {}  and params {} is started.", namedQuery, parameters);
			}
			TypedQuery<Long> query = em.createNamedQuery(namedQuery, Long.class);
			// Method that will populate parameters if they are passed not null
			// and empty
			if (parameters != null && !parameters.isEmpty()) {
				populateQueryParameters(query, parameters);
			}
			result = query.getSingleResult().intValue();
			if (log.isDebugEnabled()) {
				log.debug("Count All for {} with query {}  and params {} is finished.", namedQuery, parameters);
			}
		} catch (Exception e) {
			log.error("Error trying to find count of all records for given query {} , and with params {} ", namedQuery,
					parameters);
		}
		return result;
	}

	protected Long countAll(String filter, long companyId) throws BadRequestException, DAOException {
		if (log.isDebugEnabled())
			log.debug("Count All for SCIM filter {} is started.", filter);
		Long result = 0L;
		// Filter Processing
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<T> root = cq.from(entityClass);
		Predicate pred = getTenantPredicate(companyId, cb, root);
		try {
			pred = buildPredicateFromStringFilter(filter, cb, root, pred);
			cq.select(cb.count(root));
			if (pred != null)
				cq.where(pred);
			result = em.createQuery(cq).getSingleResult();
		} catch (Exception ex) {
			log.error("SCIM Parse exception in countAll in JPATemplate. Reason: ", ex);
			throw new BadRequestException(SCIM_PARSING_FAILED);
		}
		if (log.isDebugEnabled())
			log.debug("Count All with SCIM filter ended.");
		return result;
	}

	protected <S> S findOne(String namedQuery, Map<String, Object> parameters, Class<S> clazz) throws DAOException {
		S result = null;
		try {
			if (log.isDebugEnabled()) {
				log.debug("Find one record for {} with query {}  and params {} is started.", namedQuery, parameters);
			}
			TypedQuery<S> query = em.createNamedQuery(namedQuery, clazz);
			// Method that will populate parameters if they are passed not null
			// and empty
			if (parameters != null && !parameters.isEmpty()) {
				populateQueryParameters(query, parameters);
			}
			result = query.getSingleResult();
			if (log.isDebugEnabled()) {
				log.debug("Find one finished with {} ", result);
			}
		} catch (NonUniqueResultException | NoResultException ex) {
			log.error("Error trying to find record for given query. Exception: {} ", ex.getMessage());
			if (ex instanceof NoResultException) {
				throw new ObjectNotFoundException();
			}
			throw new DAOException(ex.getMessage());
		}
		return result;
	}

	protected <S> S find(String namedQuery, Map<String, Object> parameters, Class<S> classz) throws DAOException {
		S result = null;
		try {
			if (log.isDebugEnabled()) {
				log.debug("Find one record for {} with query {}  and params {} is started.", namedQuery, parameters);
			}
			TypedQuery<S> query = em.createNamedQuery(namedQuery, classz);
			// Method that will populate parameters if they are passed not null
			// and empty
			if (parameters != null && !parameters.isEmpty()) {
				populateQueryParameters(query, parameters);
			}
			List<S> totalResults = query.getResultList();
			if (!CollectionUtils.isEmpty(totalResults)) {
				result = totalResults.get(0);
			} else {
				if (log.isInfoEnabled()) {
					log.info("{} records found with the given query. hence returning null.", totalResults);
				}
			}
			if (log.isDebugEnabled())
				log.debug("Find one finished with {} ", result);
		} catch (Exception ex) {
			log.warn("Error trying to find record for given query. Exception: {} ", ex);
		}
		return result;
	}

	protected <S> List<S> findAll(String namedQuery, Map<String, Object> parameters, Class<S> clazz, int start,
			int count) {
		if (log.isDebugEnabled()) {
			log.debug("Find records for {} with query {}  and params {} is started.", namedQuery, parameters);
		}
		List<S> result = null;
		try {
			TypedQuery<S> query = em.createNamedQuery(namedQuery, clazz);
			// Method that will populate parameters if they are passed not null
			// and empty
			if (parameters != null && !parameters.isEmpty()) {
				populateQueryParameters(query, parameters);
			}
			// Pagination if desired.
			if (start != 0 || count != 0) {
				query.setFirstResult(start);
				query.setMaxResults(count);
			}
			result = query.getResultList();
		} catch (NoResultException | NonUniqueResultException e) {
			log.error("Found zero or more than one records for the given parameters. : " + e);
		}
		if (log.isDebugEnabled()) {
			log.debug("Find All finished.");
		}
		return result;
	}

	protected <S> void delete(String namedQuery, Map<String, Object> parameters, Class<S> clazz) throws DAOException {
		S result = null;
		try {
			TypedQuery<S> query = em.createNamedQuery(namedQuery, clazz);
			// Method that will populate parameters if they are passed not null
			// and empty
			if (parameters != null && !parameters.isEmpty()) {
				populateQueryParameters(query, parameters);
			}
			result = query.getSingleResult();
			em.remove(result);
		} catch (NonUniqueResultException | NoResultException ex) {
			log.error("Error trying to find record for given query. Exception: ", ex.getMessage());
			if (ex instanceof NoResultException) {
				throw new DAOException(ex.getMessage());
			}
		}
	}

	protected Long countAll(String filter) throws BadRequestException, DAOException {
		if (log.isDebugEnabled()) {
			log.debug("Count All for SCIM filter {} is started.", filter);
		}
		Long result = 0L;
		// Filter Processing
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<T> root = cq.from(entityClass);
		Predicate pred = null;

		try {
			pred = buildPredicateFromStringFilter(filter, cb, root, pred);
			cq.select(cb.count(root));
			if (pred != null) {
				cq.where(pred);
			}
			result = em.createQuery(cq).getSingleResult();
		} catch (Exception ex) {
			log.error("SCIM Parse exception in countAll in JPATemplate. Reason: ", ex);
			throw new BadRequestException(SCIM_PARSING_FAILED);
		}
		if (log.isDebugEnabled()) {
			log.debug("Count All with SCIM filter ended.");
		}
		return result;
	}

	private Predicate buildPredicateFromStringFilter(final String filter, final CriteriaBuilder cb, final Root<T> root,
			Predicate pred) throws MangoException {
		if (filter != null && !filter.isEmpty()) {
			final SCIMFilter scimFilter;
			scimFilter = SCIMFilter.parse(filter);
			Predicate SCIMPredicate = scimFilter.buildPredicates(scimFilter, cb, root);
			if (null != SCIMPredicate) {
				pred = (pred == null) ? SCIMPredicate : cb.and(pred, SCIMPredicate);
			}
		}
		return pred;
	}

	private CriteriaQuery<T> sortByColumn(String sortBy, String sortOrder, CriteriaBuilder cb, CriteriaQuery<T> cq,
			Root<T> root) throws BadRequestException, DAOException {
		try {
			Object t = entityClass.newInstance();
			if (t instanceof Sortable) {
				Method method = entityClass.getDeclaredMethod("getSortableColumns");
				String[] sortableArray = (String[]) method.invoke(t);

				if (!StringUtils.isEmpty(sortBy) && null != sortableArray) {
					// throw exception if sortBy column is not a valid column
					if (!Arrays.asList(sortableArray).contains(sortBy)) {
						if (log.isDebugEnabled()) {
							log.debug("sortBy column is not a valid column");
						}
						throw new BadRequestException(ERROR_SORT_BY_COLUMN);
					}
					OrderByType orderByType = OrderByType.fromString(sortOrder);
					switch (orderByType) {
					case DESC:
						cq = cq.orderBy(cb.desc(root.get(sortBy)));
						break;
					case ASC:
					default:
						cq = cq.orderBy(cb.asc(root.get(sortBy)));
						break;
					}
				}
			}
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | InstantiationException e) {
			log.error("SortByColumn error during sorting. Reason: ", e);
			throw new DAOException(SORTING_ERROR);
		}
		return cq;
	}

	protected List<T> findAll(long companyId, int start, int count, String filter, String sortBy, String sortOrder)
			throws BadRequestException, DAOException {
		if (log.isDebugEnabled())
			log.debug("Find All for SCIM filter {} is started.", filter);
		List<T> entityList = null;
		// Filter Processing
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(entityClass);
		Root<T> root = cq.from(entityClass);
		Predicate pred = getTenantPredicate(companyId, cb, root);

		try {
			pred = buildPredicateFromStringFilter(filter, cb, root, pred);
		} catch (MangoException ex) {
			log.error("SCIM Parse exception in findAll in JPATemplate. Reason: ", ex);
			throw new BadRequestException(SCIM_PARSING_FAILED);
		}
		if (pred != null)
			cq.where(pred);
		// sortBy and sortOrder
		cq = sortByColumn(sortBy, sortOrder, cb, cq, root);
		TypedQuery<T> query = em.createQuery(cq);
		// Pagination if desired.
		if (start != 0 || count != 0) {
			query.setFirstResult(start);
			query.setMaxResults(count);
		}
		entityList = query.getResultList();
		if (log.isDebugEnabled())
			log.debug("Find All with SCIM filter ended.");
		return entityList;
	}
}
