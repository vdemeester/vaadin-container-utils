package org.shortbrain.vaadin.container;

import java.util.Collection;

import com.vaadin.data.Container;
import com.vaadin.data.Item;

/**
 * ContainerFactory interface class that define methods to facilitate the
 * creation of Containers from a list of objects using different algorithms.
 * 
 * TODO: Rename this class to ContainerFactory
 * 
 * @author Vincent Demeester <vincent@demeester.fr>
 * 
 * @param <BEAN>
 *            type of the beans.
 */
public interface IContainerFactory<BEAN> {

    /**
     * Return a container of type BEAN from a collection of BEAN objects. It will
     * update the given container if no null.
     * 
     * @param container
     *            container to be populated.
     * @param beans
     *            collection of beans.
     * @return a container of Beans.
     * 
     * @since 0.2.0
     */
    public abstract Container getContainerFromCollection(Container container,
            Collection<BEAN> beans);

    /**
     * Return a container of type BEAN from a collection of BEAN objects. It will
     * update the given container if no null.
     * 
     * The returned container will be of the given type (containerClass).
     * 
     * @param container
     *            container to be populated.
     * @param beans
     *            collection of beans.
     * @param containerClass
     *            type of the container to return.
     * @return a container of Beans.
     * 
     * @since 0.2.0
     */
    public abstract Container getContainerFromCollection(Container container,
            Collection<BEAN> beans, Class<? extends Container> containerClass);

    /**
     * Return a container of type BEAN from a collection of BEAN objects.
     * 
     * The returned container will be of the given type (containerClass).
     * 
     * @param beans
     *            collection of beans.
     * @param containerClass
     *            type of the container to return.
     * @return a container of Beans.
     * 
     * @since 0.2.0
     */
    public abstract Container getContainerFromCollection(Collection<BEAN> beans,
            Class<? extends Container> containerClass);

    /**
     * Return a new Item of type BEAN.
     * 
     * @param bean
     *            the bean.
     * 
     * @return a Item
     * @since 0.3.0
     */
    public abstract Item newItem(BEAN bean);
}
