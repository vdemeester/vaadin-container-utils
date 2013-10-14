package org.xgbi.vaadin.container;

import java.util.Collection;

import com.vaadin.data.Container.Sortable;
import com.vaadin.data.Item;

public class WrappedSortable extends WrappedContainer implements Sortable {

	private static final long serialVersionUID = 8408178489227034376L;

	public WrappedSortable(Sortable container) {
		super(container);
	}

	public Sortable getContainer() {
		return (Sortable) super.getContainer();
	}

	@Override
	public Object nextItemId(Object itemId) {
		return getContainer().nextItemId(itemId);
	}

	@Override
	public Object prevItemId(Object itemId) {
		return getContainer().prevItemId(itemId);
	}

	@Override
	public Object firstItemId() {
		return getContainer().firstItemId();
	}

	@Override
	public Object lastItemId() {
		return getContainer().lastItemId();
	}

	@Override
	public boolean isFirstId(Object itemId) {
		return getContainer().isFirstId(itemId);
	}

	@Override
	public boolean isLastId(Object itemId) {
		return getContainer().isLastId(itemId);
	}

	@Override
	public Object addItemAfter(Object previousItemId)
			throws UnsupportedOperationException {
		return getContainer().addItemAfter(previousItemId);
	}

	@Override
	public Item addItemAfter(Object previousItemId, Object newItemId)
			throws UnsupportedOperationException {
		return getContainer().addItemAfter(previousItemId, newItemId);
	}

	@Override
	public void sort(Object[] propertyId, boolean[] ascending) {
		getContainer().sort(propertyId, ascending);
	}

	@Override
	public Collection<?> getSortableContainerPropertyIds() {
		return getContainer().getSortableContainerPropertyIds();
	}

}
