package org.xgbi.vaadin.container;

import java.util.Collection;

import org.xgbi.vaadin.container.property.PropertyWrapper;

import com.vaadin.data.Container;

public interface ContainerWrapper extends Container {

	boolean addPropertyWrapper(PropertyWrapper wrapper);
	
	void addAllPropertyWrapper(Collection<PropertyWrapper> wrappers);
	
	Container getContainer();
}
