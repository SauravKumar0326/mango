package com.mango.utils;

public class Constants {
	/**
	 * The URI of the SCIM Core schema.
	 */
	public static final String SCHEMA_URI_CORE = "urn:scim:schemas:core:1.0";

	/**
	 * If the request did not contain valid data
	 */
	public static final String INVALID_REQUEST = "Invalid request";

	/**
	 * Message to show for 404 cases.
	 */
	public static final String RESOURCE_NOT_FOUND = "Resource Not Found";

	/**
	 * If SCIM parsing fails
	 */
	public static final String SCIM_PARSING_FAILED = "Error in parsing SCIM Filter";

	/**
	 * If the given column is not sortable or
	 */
	public static final String ERROR_SORT_BY_COLUMN = "Given sortBy column is invalid";

	/**
	 * Error occurs while sorting
	 */
	public static final String SORTING_ERROR = "Error while sorting";

	public static final String MANGO_SERVICE_ID = "mango";

	public static final String SERVICE_MANGO = "/services/" + MANGO_SERVICE_ID;

	public static final String SERVICE_MANGO_NOP = "/services/" + MANGO_SERVICE_ID + "/1.0/nop";

	public static final String SERVICE_MANGO_COMPANY = "/services/" + MANGO_SERVICE_ID + "/1.0/company";

	public static final String SERVICE_MANGO_RESELLER = "/services/" + MANGO_SERVICE_ID
			+ "/1.0/company/{companyId}/reseller";

	public static final String SERVICE_MANGO_SUPPLIER = "/services/" + MANGO_SERVICE_ID
			+ "/1.0/company/{companyId}/supplier";

	public static final String SERVICE_MANGO_RESELLERREPORTS = "/services/" + MANGO_SERVICE_ID
			+ "/1.0/company/{companyId}/reseller_reports";

	public static final String SERVICE_MANGO_SUPPLIERREPORTS = "/services/" + MANGO_SERVICE_ID
			+ "/1.0/company/{companyId}/supplier_reports";

	public static final String LOGGING_CSV_DELIMITER = "####";
	/**
	 * Resource already exists
	 */
	public static final String RESOURCE_ALREADY_EXISTS = "Resource with this name already exists";
}
