package com.mango.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mango.utils.Constants;

public class AttributePath {

	private static final Pattern pattern = Pattern.compile("^((.+):)?([^.]+)(\\.(.+))?$");

	/**
	 * The URI of the attribute schema.
	 */
	private final String attributeSchema;

	/**
	 * The name of the attribute.
	 */
	private final String attributeName;

	/**
	 * The name of the sub-attribute, or {@code null} if absent.
	 */
	private final String subAttributeName;

	public AttributePath(final String attributeSchema, final String attributeName, final String subAttributeName) {
		this.attributeSchema = attributeSchema;
		this.attributeName = attributeName;
		this.subAttributeName = subAttributeName;
	}

	public static AttributePath parse(final String path) {
		final Matcher matcher = pattern.matcher(path);

		if (!matcher.matches() || matcher.groupCount() != 5) {
			throw new IllegalArgumentException(
					String.format("'%s' does not match '[schema:]attr[.sub-attr]' format", path));
		}

		final String attributeSchema = matcher.group(2);
		final String attributeName = matcher.group(3);
		final String subAttributeName = matcher.group(5);

		if (attributeSchema != null) {
			return new AttributePath(attributeSchema, attributeName, subAttributeName);
		} else {
			return new AttributePath(Constants.SCHEMA_URI_CORE, attributeName, subAttributeName);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		toString(builder);
		return builder.toString();
	}

	public void toString(final StringBuilder builder) {
		if (!attributeSchema.equalsIgnoreCase(Constants.SCHEMA_URI_CORE)) {
			builder.append(attributeSchema);
			builder.append(':');
		}

		builder.append(attributeName);
		if (subAttributeName != null) {
			builder.append('.');
			builder.append(subAttributeName);
		}
	}

	/**
	 * Retrieve the URI of the attribute schema.
	 *
	 * @return The URI of the attribute schema.
	 */
	public String getAttributeSchema() {
		return attributeSchema;
	}

	/**
	 * Retrieve the name of the attribute.
	 *
	 * @return The name of the attribute.
	 */
	public String getAttributeName() {
		return attributeName;
	}

	/**
	 * Retrieve the name of the sub-attribute, or {@code null} if absent.
	 *
	 * @return The name of the sub-attribute, or {@code null} if absent.
	 */
	public String getSubAttributeName() {
		return subAttributeName;
	}
}