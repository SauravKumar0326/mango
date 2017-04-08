package com.mango.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListResponse<T> {

	private Long totalResults;
	private Integer itemsPerPage;
	private Integer startIndex;

	@JsonProperty("Resources")
	private Collection<T> resources = new ArrayList<T>();

	private Collection<String> categories = new ArrayList<>();

	/**
	 * Default constructor for Jackson
	 */
	public ListResponse() {
	}

	/**
	 * Overloaded constructor which will map the entity to dto. This one is
	 * specifically for Settings DTOs.
	 *
	 * @param totalResults
	 * @param itemsPerPage
	 * @param startIndex
	 * @param resources
	 */
	public ListResponse(Long totalResults, Integer itemsPerPage, Integer startIndex, Collection<T> resources) {
		this.resources = resources;
		this.totalResults = totalResults;
		this.itemsPerPage = itemsPerPage;
		this.startIndex = startIndex;
	}

	/**
	 * Overloaded constructor which will map the entity to dto. This one is
	 * specifically for Settings DTOs.
	 *
	 * @param totalResults
	 * @param itemsPerPage
	 * @param startIndex
	 * @param resources
	 * @param categories
	 */
	public ListResponse(Long totalResults, Integer itemsPerPage, Integer startIndex, Collection<T> resources,
			Collection<String> categories) {
		this.resources = resources;
		this.totalResults = totalResults;
		this.itemsPerPage = itemsPerPage;
		this.startIndex = startIndex;
		this.categories = categories;
	}

	/**
	 * gets a list of found {@link ApplicationDto}s or {@link ContentDto}s or
	 * {@link SettingDto}s
	 *
	 * @return a list of found resources
	 */
	public Collection<T> getResources() {
		return resources;
	}

	/**
	 * The total number of results returned by the list or query operation. This
	 * may not be equal to the number of elements in the Resources attribute of
	 * the list response if pagination is requested.
	 *
	 * @return the total result
	 */
	public Long getTotalResults() {
		return totalResults;
	}

	/**
	 * The number of Resources returned in a list response page.
	 *
	 * @return items per page
	 */
	public Integer getItemsPerPage() {
		return itemsPerPage;
	}

	/**
	 * The 1-based index of the first result in the current set of list results.
	 *
	 * @return the start index of the actual page
	 */
	public Integer getStartIndex() {
		return startIndex;
	}

	/**
	 * Total number of results.
	 *
	 * @param totalResults
	 */
	public void setTotalResults(Long totalResults) {
		this.totalResults = totalResults;
	}

	/**
	 * Items per page.
	 *
	 * @param itemsPerPage
	 */
	public void setItemsPerPage(Integer itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	/**
	 * Starting value from where results needs to be retrieved.
	 *
	 * @param startIndex
	 */
	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	/**
	 * Set Result list
	 *
	 * @param resources
	 */
	public void setResources(Set<T> resources) {
		this.resources = resources;
	}

	/**
	 * Get Result list
	 * 
	 * @param resources
	 */
	@JsonSetter("resources")
	public void setResourcesSmallcase(Set<T> resources) {
		this.resources = resources;
	}

	/**
	 * @return Categories list
	 */
	public Collection<String> getCategories() {
		return categories;
	}

	/**
	 * Set categories List
	 * 
	 * @param categories
	 */
	public void setCategories(Collection<String> categories) {
		this.categories = categories;
	}

	/**
	 * recordsTotal and recordsFiltered are required by the UI for pagination.
	 * They hold value of totalResults
	 * 
	 * @return
	 */
	@JsonAnyGetter
	public Map<String, Object> getAttributesForPagination() {
		Map<String, Object> paginationAttribures = new HashMap<String, Object>(2);
		long totalResults = this.totalResults == null ? 0 : this.totalResults.longValue();
		paginationAttribures.put("recordsTotal", totalResults);
		paginationAttribures.put("recordsFiltered", totalResults);
		return paginationAttribures;
	}
}
