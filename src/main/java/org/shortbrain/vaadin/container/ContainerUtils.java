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
	public static Container initContainer(
			Class<? extends Container> containerClass)
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

	/**
	 * Add a property to the given container using {@link PropertyMetadata}.
	 * 
	 * @see ContainerUtils#addContainerProperty(Container, Object, Class,
	 *      Object)
	 * 
	 * @param container
	 *            the container.
	 * @param propertyMetadata
	 *            the porperty metadata.
	 */
	public static void addContainerProperty(Container container,
			PropertyMetadata propertyMetadata) {
		addContainerProperty(container, propertyMetadata.getPropertyName(),
				propertyMetadata.getPropertyClass(),
				propertyMetadata.getDefaultValue());
	}

	/**
	 * Add a property to the given container.
	 * 
	 * @param container
	 *            the container.
	 * @param propertyName
	 *            the name of the property.
	 * @param propertyClass
	 *            the type of the property.
	 * @param propertyDefaultValue
	 *            the default value of the propertyx
	 */
	public static void addContainerProperty(Container container,
			Object propertyName, Class<?> propertyClass,
			Object propertyDefaultValue) {
		if (propertyName == null || propertyClass == null) {
			throw new NullPointerException(
					"propertyName and propertyClass cannot be null.");
		}
		container.addContainerProperty(propertyName, propertyClass,
				propertyDefaultValue);
	}
}
