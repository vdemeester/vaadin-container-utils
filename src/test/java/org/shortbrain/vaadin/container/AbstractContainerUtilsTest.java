/*
 * Copyright 2012 Vincent Demeester<vincent+shortbrain@demeester.fr>.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.shortbrain.vaadin.container;

import static org.junit.Assert.*;

import java.util.Collection;

import org.shortbrain.vaadin.container.property.PropertyMetadata;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;

/**
 * Abstract test class destined to be the parent of all tests in
 * vaadin-container-utils.
 * 
 * @author Vincent Demeester <vincent@demeester.fr>
 * 
 */
public abstract class AbstractContainerUtilsTest {

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

	/**
	 * Assert helper to test an {@link Item}.
	 * 
	 * @param properties
	 *            properties that should be in item.
	 * @param values
	 *            values that should be in property.
	 * @param item
	 *            the item to test.
	 */
	protected void assertItem(String[] properties, Object[] values, Item item) {
		assertNotNull("item should not be null.",item);
		// Check properties
		assertProperties(properties, item.getItemPropertyIds());
		for (String property : properties) {
			assertTrue(item.getItemPropertyIds().contains(property));
		}
		// Check values
		for (int i = 0; i < values.length; i++) {
			String property = properties[i];
			Property vaadinProperty = item.getItemProperty(property);
			assertNotNull(vaadinProperty);
			// If expected property is null don't verify it
			if (values[i] != null) {
				assertEquals(values[i], vaadinProperty.getValue());
			}
		}
	}

	/**
	 * Assert helper for test Properties from Vaadin {@link Container}.
	 * 
	 * @param properties
	 *            properties expected.
	 * @param itemPropertyIds
	 *            properties to test.
	 */
	protected void assertProperties(String[] properties,
			Collection<?> itemPropertyIds) {
		assertNotNull(itemPropertyIds);
		assertEquals(properties.length, itemPropertyIds.size());
		for (String property : properties) {
			assertTrue(itemPropertyIds.contains(property));
		}
	}
}