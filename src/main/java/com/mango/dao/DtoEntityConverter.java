package com.mango.dao;

import com.mango.exception.DAOException;

public interface DtoEntityConverter<T, E> {
	/**
	 * Convert dtoObject to entityObject.
	 * 
	 * @param dtoObject
	 *            DTO object to convert from
	 * @param entityObject
	 *            Entity object to convert to
	 * @throws DAOException
	 */
	void dtoToEntityConvert(final T dto, E entity) throws DAOException;
}
