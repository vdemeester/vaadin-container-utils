package org.xgbi.vaadin.container;

import com.vaadin.data.Container.Filterable;
import com.vaadin.data.util.filter.UnsupportedFilterException;

public class WrappedFilterable extends WrappedContainer implements Filterable {

	private static final long serialVersionUID = 8408178489227034376L;

	public WrappedFilterable(Filterable container) {
		super(container);
	}

	@Override
	public void addContainerFilter(Filter filter)
			throws UnsupportedFilterException {
		getContainer().addContainerFilter(filter);
	}

	@Override
	public void removeContainerFilter(Filter filter) {
		getContainer().removeContainerFilter(filter);
	}

	@Override
	public void removeAllContainerFilters() {
		getContainer().removeAllContainerFilters();
	}
	
	public Filterable getContainer() {
		return (Filterable) super.getContainer();
	}

}
