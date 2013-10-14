package org.xgbi.vaadin.container.item;

import java.util.Collection;

import org.xgbi.vaadin.container.property.PropertyWrapper;

import com.vaadin.data.Item;

public interface ItemWrapper extends Item {

	boolean addPropertyWrapper(PropertyWrapper wrapper);
	
	void addAllPropertyWrapper(Collection<PropertyWrapper> wrappers);
	
	Item getItem();
}
