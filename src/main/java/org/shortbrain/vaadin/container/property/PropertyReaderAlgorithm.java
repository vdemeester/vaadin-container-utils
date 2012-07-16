package org.shortbrain.vaadin.container.property;

import java.util.List;


/**
 * Property Reader algorithm interface, used in ContainerFactory to build the
 * Property of the container.
 * 
 * @author Vincent Demeester <vincent@demeester.fr>
 * 
 */
public interface PropertyReaderAlgorithm {

	List<PropertyMetadata> getProperties(Class<?> beanClass);
}
