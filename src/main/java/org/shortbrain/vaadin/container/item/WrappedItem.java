package org.shortbrain.vaadin.container.item;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.vaadin.data.Item;
import com.vaadin.data.Property;

/**
 * {@link Item} that can wrapped properties.
 * 
 * <p>
 * Note that property wrapper are nothing more than implementation of Property
 * </p>
 * 
 * @author Vincent Demeester <vdemeester@sbr.io>
 * 
 */
public class WrappedItem implements Item {

	private static final long serialVersionUID = -6090435499457567361L;

	public Map<Object, Property> wrappedProperties = new HashMap<Object, Property>();
	public final Item item;

	public WrappedItem(Item item) {
		this.item = item;
	}

	public void addWrappedProperty(Object propertyId, Property wrappedProperty) {
		this.wrappedProperties.put(propertyId, wrappedProperty);
	}

	@Override
	public Property getItemProperty(Object id) {
		if (wrappedProperties.containsKey(id)) {
			return wrappedProperties.get(id);
		}
		return item.getItemProperty(id);
	}

	@Override
	public Collection<?> getItemPropertyIds() {
		return item.getItemPropertyIds();
	}

	@Override
	public boolean addItemProperty(Object id, Property property)
			throws UnsupportedOperationException {
		return item.addItemProperty(id, property);
	}

	@Override
	public boolean removeItemProperty(Object id)
			throws UnsupportedOperationException {
		return item.removeItemProperty(id);
	}

	public Item getItem() {
		return item;
	}

}