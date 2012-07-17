package org.shortbrain.vaadin.container;

import static org.junit.Assert.*;

import org.shortbrain.vaadin.container.property.PropertyMetadata;

public class AbstractContainerUtilsTest {

	/**
	 * Assert helper to test a {@link PropertyMetadata}.
	 * 
	 * @param name
	 *            name of the metadata.
	 * @param type
	 *            type of the metadata.
	 * @param defaultValue
	 *            default value of the metadata.
	 * @param attribute
	 *            attribute of the metadata.
	 * @param metadata
	 *            the metadata to be asserted.
	 */
	protected void assertMetadata(String name, Class<?> type,
			Object defaultValue, String attribute, PropertyMetadata metadata) {
		assertEquals(name, metadata.getPropertyName());
		assertEquals(type, metadata.getPropertyClass());
		assertEquals(defaultValue, metadata.getDefaultValue());
		assertEquals(attribute, metadata.getPropertyAttribute());
	}
}