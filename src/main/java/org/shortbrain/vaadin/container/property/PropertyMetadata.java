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
