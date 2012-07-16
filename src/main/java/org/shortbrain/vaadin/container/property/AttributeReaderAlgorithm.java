package org.shortbrain.vaadin.container.property;

import java.beans.PropertyDescriptor;
import java.beans.Transient;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * Implementation of {@link PropertyReaderAlgorithm} that looks for attribute of
 * the beanClass
 * 
 * The attribute annotated with {@link Transient} will be ignored.
 * 
 * @author Vincent Demeester <vincent@demeester.fr>
 * 
 */
public class AttributeReaderAlgorithm implements PropertyReaderAlgorithm {

	/**
	 * List of attribute to ignore.
	 */
	private final Collection<String> ignoredAttributes;

	public AttributeReaderAlgorithm() {
		this(Arrays.asList(new String[] { "serialVersionUID" }));
	}

	public AttributeReaderAlgorithm(Collection<String> ignoredAttributes) {
		this.ignoredAttributes = ignoredAttributes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PropertyMetadata> getProperties(Class<?> beanClass) {
		List<PropertyMetadata> metadatas = new LinkedList<PropertyMetadata>();
		for (PropertyDescriptor propertyDescriptor : PropertyUtils
				.getPropertyDescriptors(beanClass)) {
			if (!ignoredAttributes.contains(propertyDescriptor.getName())) {
				String propertyName = propertyDescriptor.getName();
				Class<?> propertyClass = propertyDescriptor.getPropertyType();
				String propertyAttribute = propertyDescriptor.getName();
				PropertyMetadata metadata = new PropertyMetadata(propertyName,
						propertyClass, null, propertyAttribute);
				metadatas.add(metadata);
			}
		}
		return metadatas;
	}
}
