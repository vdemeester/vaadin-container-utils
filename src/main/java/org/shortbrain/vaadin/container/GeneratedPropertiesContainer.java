package org.shortbrain.vaadin.container;

import org.shortbrain.vaadin.container.property.PropertyGenerator;

/**
 * 
 * @author Vincent
 *
 */
public interface GeneratedPropertiesContainer<BEANTYPE> {

	boolean addGeneratedContainerProperty(String propertyId, PropertyGenerator<?, BEANTYPE> generator);
	
}
