package org.xgbi.vaadin.container;

import java.util.Collection;

import com.vaadin.data.Container.Hierarchical;

public class WrappedHierarchical extends WrappedContainer implements
		Hierarchical {

	private static final long serialVersionUID = 3091425919414633385L;

	public WrappedHierarchical(Hierarchical container) {
		super(container);
	}

	@Override
	public Collection<?> getChildren(Object itemId) {
		return getContainer().getChildren(itemId);
	}

	@Override
	public Object getParent(Object itemId) {
		return getContainer().getParent(itemId);
	}

	@Override
	public Collection<?> rootItemIds() {
		return getContainer().rootItemIds();
	}

	@Override
	public boolean setParent(Object itemId, Object newParentId)
			throws UnsupportedOperationException {
		return getContainer().setParent(itemId, newParentId);
	}

	@Override
	public boolean areChildrenAllowed(Object itemId) {
		return getContainer().areChildrenAllowed(itemId);
	}

	@Override
	public boolean setChildrenAllowed(Object itemId, boolean areChildrenAllowed)
			throws UnsupportedOperationException {
		return getContainer().setChildrenAllowed(itemId, areChildrenAllowed);
	}

	@Override
	public boolean isRoot(Object itemId) {
		return getContainer().isRoot(itemId);
	}

	@Override
	public boolean hasChildren(Object itemId) {
		return getContainer().hasChildren(itemId);
	}

	public Hierarchical getContainer() {
		return (Hierarchical) super.getContainer();
	}
}
