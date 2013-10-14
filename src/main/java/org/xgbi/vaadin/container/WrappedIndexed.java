package org.xgbi.vaadin.container;

import com.vaadin.data.Container.Indexed;
import com.vaadin.data.Item;

public class WrappedIndexed extends WrappedContainer implements Indexed {

	private static final long serialVersionUID = 8408178489227034376L;

	public WrappedIndexed(Indexed container) {
		super(container);
	}

	public Indexed getContainer() {
		return (Indexed) super.getContainer();
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
	public int indexOfId(Object itemId) {
		return getContainer().indexOfId(itemId);
	}

	@Override
	public Object getIdByIndex(int index) {
		return getContainer().getIdByIndex(index);
	}

	@Override
	public Object addItemAt(int index) throws UnsupportedOperationException {
		return getContainer().addItemAt(index);
	}

	@Override
	public Item addItemAt(int index, Object newItemId)
			throws UnsupportedOperationException {
		return getContainer().addItemAt(index, newItemId);
	}

}
