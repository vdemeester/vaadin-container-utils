package org.xgbi.vaadin.container.property;

import com.vaadin.data.Item;
import com.vaadin.data.Property;

public interface PropertyWrapper {

	Property getProperty(Item item);
	
	Object getPropertyId();

}
