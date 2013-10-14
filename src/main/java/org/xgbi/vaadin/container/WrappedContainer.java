package org.xgbi.vaadin.container;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.xgbi.vaadin.container.item.ItemWrapper;
import org.xgbi.vaadin.container.item.WrappedItem;
import org.xgbi.vaadin.container.property.PropertyWrapper;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;

/**
 * Container that wraps a real container to be able to customize property.
 * 
 * @author Vincent Demeester
 * 
 */
public class WrappedContainer implements ContainerWrapper {

	private static final long serialVersionUID = 8560562848589445512L;
	
	private final Container container;
	private Map<Object, PropertyWrapper> wrappers;

	public WrappedContainer(Container container) {
		checkArgument(container != null,
				"The container to be wrapped should not be null.");
		this.container = container;
		this.wrappers = new HashMap<Object, PropertyWrapper>();
	}

	@Override
	public Item getItem(Object itemId) {
		Item item = container.getItem(itemId);
		ItemWrapper itemWrapper = new WrappedItem(item);
		itemWrapper.addAllPropertyWrapper(wrappers.values());
		return itemWrapper;
	}

	@Override
	public Property getContainerProperty(Object itemId, Object propertyId) {
		if (wrappers.containsKey(propertyId)) {
			PropertyWrapper wrapper = wrappers.get(propertyId);
			return wrapper.getProperty(container.getItem(itemId));
		}
		return container.getContainerProperty(itemId, propertyId);
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
	public Collection<?> getContainerPropertyIds() {
		return container.getContainerPropertyIds();
	}

	@Override
	public Collection<?> getItemIds() {
		return container.getItemIds();
	}

	@Override
	public Class<?> getType(Object propertyId) {
		return container.getType(propertyId);
	}

	@Override
	public int size() {
		return container.size();
	}

	@Override
	public boolean containsId(Object itemId) {
		return container.containsId(itemId);
	}

	@Override
	public Item addItem(Object itemId) throws UnsupportedOperationException {
		return container.addItem(itemId);
	}

	@Override
	public Object addItem() throws UnsupportedOperationException {
		return container.addItem();
	}

	@Override
	public boolean removeItem(Object itemId)
			throws UnsupportedOperationException {
		return container.removeItem(itemId);
	}

	@Override
	public boolean addContainerProperty(Object propertyId, Class<?> type,
			Object defaultValue) throws UnsupportedOperationException {
		return container.addContainerProperty(propertyId, type, defaultValue);
	}

	@Override
	public boolean removeContainerProperty(Object propertyId)
			throws UnsupportedOperationException {
		return container.removeContainerProperty(propertyId);
	}

	@Override
	public boolean removeAllItems() throws UnsupportedOperationException {
		return container.removeAllItems();
	}

	// Visible for testing
	protected Map<Object, PropertyWrapper> getWrappers() {
		return wrappers;
	}
	
	public Container getContainer() {
		return this.container;
	}
}
