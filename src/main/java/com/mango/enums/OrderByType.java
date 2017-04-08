package com.mango.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OrderByType {

	ASC("asc"),

	DESC("desc");

	private final String value;

	private OrderByType(String value) {
		this.value = value;
	}

	/**
	 * @return the string representing OrderBy Type.
	 */
	public String getValue() {
		return value;
	}

	@JsonCreator
	public static OrderByType fromString(String text) {
		if (text != null) {
			for (OrderByType s : OrderByType.values()) {
				if (text.toLowerCase().contains(s.getValue())) {
					return s;
				}
			}
		}
		return ASC;
	}
}