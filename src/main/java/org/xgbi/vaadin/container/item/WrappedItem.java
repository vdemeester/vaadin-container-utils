package org.xgbi.vaadin.container.item;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.xgbi.vaadin.container.property.PropertyWrapper;

import com.vaadin.data.Item;
import com.vaadin.data.Property;

public class WrappedItem implements ItemWrapper {

	private static final long serialVersionUID = -8957404894159204448L;
	
	private final Item item;
	private Map<Object, PropertyWrapper> wrappers;

	public WrappedItem(Item item) {
		checkArgument(item != null,
				"The container to be wrapped should not be null.");
		this.item = item;
		this.wrappers = new HashMap<Object, PropertyWrapper>();
	}

	@Override
	public boolean addPropertyWrapper(PropertyWrapper wrapper) {
		return wrappers.put(wrapper.getPropertyId(), wrapper) != null;
	}

	@Override
	public void addAllPropertyWrapper(Collection<PropertyWrapper> wrappers) {
		for(PropertyWrapper wrapper : wrappers) {
			addPropertyWrapper(wrapper);
		}
	}

	@Override
	public Property getItemProperty(Object id) {
		if (wrappers.containsKey(id)) {
			PropertyWrapper wrapper = wrappers.get(id);
			return wrapper.getProperty(item);
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
		if (this.item instanceof WrappedItem) {
			// Return the real item at all time (even when wrapped in wrapped)
			return ((WrappedItem) item).getItem();
		}
		return this.item;
	}

}
