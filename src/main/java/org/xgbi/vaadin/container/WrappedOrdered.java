package org.xgbi.vaadin.container;

import com.vaadin.data.Container.Ordered;
import com.vaadin.data.Item;

public class WrappedOrdered extends WrappedContainer implements Ordered {

	private static final long serialVersionUID = 8408178489227034376L;

	public WrappedOrdered(Ordered container) {
		super(container);
	}

	public Ordered getContainer() {
		return (Ordered) super.getContainer();
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

}
