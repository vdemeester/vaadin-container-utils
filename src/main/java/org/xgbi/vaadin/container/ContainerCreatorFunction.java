package org.xgbi.vaadin.container;

import com.vaadin.data.Container;

public interface ContainerCreatorFunction<T extends Container> {

	/**
	 * Returns a newly created {@link Container}.
	 * @return
	 */
	T newInstance();
}
