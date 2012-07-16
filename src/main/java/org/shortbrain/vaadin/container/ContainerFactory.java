package org.shortbrain.vaadin.container;

import java.util.List;

import com.vaadin.data.Container;

/**
 * ContainerFactory interface that define methods to facilitate the creation of
 * Containers from a list of objects using different algorithms.
 * 
 * @author Vincent Demeester <vincent@demeester.fr>
 * 
 * @param <BEAN>
 *            type of the beans.
 */
public interface ContainerFactory<BEAN> {

	/**
	 * Return a container of type BEAN from a list of BEAN objects. It will
	 * update the given container if no null.
	 * 
	 * @param container
	 *            container to be populated.
	 * @param beans
	 *            list of beans.
	 * @return a container of Beans.
	 */
	public Container getContainerFromList(Container container, List<BEAN> beans);

	/**
	 * Return a container of type BEAN from a list of BEAN objects. It will
	 * update the given container if no null.
	 * 
	 * The returned container will be of the given type (containerClass).
	 * 
	 * @param container
	 *            container to be populated.
	 * @param beans
	 *            list of beans.
	 * @param containerClass
	 *            type of the container to return.
	 * @return a container of Beans.
	 */
	public Container getContainerFromList(Container container,
			List<BEAN> beans, Class<? extends Container> containerClass);

	/**
	 * Return a container of type BEAN from a list of BEAN objects.
	 * 
	 * The returned container will be of the given type (containerClass).
	 * 
	 * @param beans
	 *            list of beans.
	 * @param containerClass
	 *            type of the container to return.
	 * @return a container of Beans.
	 */
	public Container getContainerFromList(List<BEAN> beans,
			Class<? extends Container> containerClass);
}
