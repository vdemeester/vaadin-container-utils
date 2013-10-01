package org.xgbi.vaadin.container;

import org.xgbi.vaadin.container.property.PropertyGenerator;

/**
 * 
 * @author Vincent
 *
 */
public interface GeneratedPropertiesContainer<BEANTYPE> {

	boolean addGeneratedContainerProperty(String propertyId, PropertyGenerator<?, BEANTYPE> generator);
	
}
