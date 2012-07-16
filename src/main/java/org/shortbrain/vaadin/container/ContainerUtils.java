package org.shortbrain.vaadin.container;


import com.vaadin.data.Container;
import com.vaadin.data.Container.Filterable;
import com.vaadin.data.Container.Hierarchical;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.data.util.IndexedContainer;

/**
 * Util classes for vaadin Container.
 *
 * @author Vincent Demeester <vincent+work@demeester.fr>s
 *
 */
public final class ContainerUtils {

    /**
     * Private constructor to force the non-instantiation of this class.
     */
    private ContainerUtils() {
    }

    /**
     * Initialize a container with the given type.
     *
     * @param containerClass
     *            type of the container
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Container initContainer(Class<? extends Container> containerClass)
            throws InstantiationException, IllegalAccessException {
        Container container = null;
        if (containerClass.isInterface()) {
            if (containerClass == Filterable.class) {
                // Default Filterable is Indexed
                container = new IndexedContainer();
            } else if (containerClass == Hierarchical.class) {
                // Default Hierarchical is HierarchicalContainer
                container = new HierarchicalContainer();
            } else {
                throw new InstantiationException(containerClass
                        + " not supported.");
            }
        } else {
            container = containerClass.newInstance();
        }
        return container;
    }
}
