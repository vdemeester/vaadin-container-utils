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
package org.shortbrain.vaadin.container.property;

/**
 * Metedata used to create a property of a Vaadin Container.
 * 
 * @author Vincent Demeester <vincent@demeeester.fr>
 * 
 */
public class PropertyMetadata {

	/**
	 * Name of the property.
	 */
	private final String propertyName;
	/**
	 * Type of the property.
	 */
	private final Class<?> propertyClass;
	/**
	 * Default value of the property.
	 */
	private final Object defaultValue;
	/**
	 * Attribute of the property – might be used to get the value of the
	 * property.
	 */
	private final String propertyAttribute;

	/**
	 * Creates a {@link PropertyMetadata}
	 * 
	 * @param propertyName
	 * @param propertyClass
	 * @param defaultValue
	 * @param propertyAttribute
	 */
	public PropertyMetadata(String propertyName, Class<?> propertyClass,
			Object defaultValue, String propertyAttribute) {
		super();
		this.propertyName = propertyName;
		this.propertyClass = propertyClass;
		this.defaultValue = defaultValue;
		this.propertyAttribute = propertyAttribute;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public Class<?> getPropertyClass() {
		return propertyClass;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public String getPropertyAttribute() {
		return propertyAttribute;
	}

}
