/*
 * Copyright 2012 Vincent Demeester<vincent+shortbrain@demeester.fr>.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.xgbi.vaadin.container;

import java.util.Collection;

import org.xgbi.vaadin.container.property.PropertyMetadata;

import com.vaadin.data.Container;
import com.vaadin.data.Container.Filterable;
import com.vaadin.data.Container.Hierarchical;
import com.vaadin.data.Item;
import com.vaadin.data.util.AbstractBeanContainer;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.data.util.IndexedContainer;

/**
 * Util classes for vaadin Container.
 * 
 * @author Vincent Demeester <vincent@demeester.fr>
 * 
 */
public final class ContainerUtils {

	/**
	 * Private constructor to force the non-instantiation of this class.
	 */
	private ContainerUtils() {
	}

	public static Container getFromPrimitiveCollection(Collection<?> primitives) {
		IndexedContainer container = new IndexedContainer();
		for (Object o : primitives) {
			container.addItem(o);
		}
		return container;
	}

	/**
	 * Initialize a container with the given type.
	 * 
	 * @param containerClass
	 *            type of the container
	 * @return a container.
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws IllegalArgumentException
	 *             if containerClass is null.
	 */
	public static Container initContainer(
			Class<? extends Container> containerClass)
			throws InstantiationException, IllegalAccessException {
		if (containerClass == null) {
			throw new IllegalArgumentException("containerClass cannot be null.");
		}
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
		if (container instanceof AbstractBeanContainer<?, ?>) {
			addNestedContainerProperty((AbstractBeanContainer<?, ?>) container,
					propertyMetadata);
		} else {
			addContainerProperty(container, propertyMetadata.getPropertyName(),
					propertyMetadata.getPropertyClass(),
					propertyMetadata.getDefaultValue());
		}
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

	/**
	 * Add a property to the given container using {@link PropertyMetadata}.
	 * 
	 * @see ContainerUtils#addNestedContainerProperty(AbstractBeanContainer,
	 *      String)
	 * 
	 * @param container
	 *            the container.
	 * @param propertyMetadata
	 *            the porperty metadata.
	 * 
	 * @since 0.2.0
	 */
	public static void addNestedContainerProperty(
			AbstractBeanContainer<?, ?> container,
			PropertyMetadata propertyMetadata) {
		if (propertyMetadata.getPropertyAttribute() != null) {
			if (container instanceof AliasContainer) {
				addShortcutContainerProperty((AliasContainer) container,
						propertyMetadata.getPropertyName(),
						propertyMetadata.getPropertyAttribute());
			} else {
				addNestedContainerProperty(container,
						propertyMetadata.getPropertyAttribute());
			}
		} else {
			addNestedContainerProperty(container,
					propertyMetadata.getPropertyName());
		}
	}

	/**
	 * Add a property to the given container (AbstractBeanContainer).
	 * 
	 * @param container
	 *            the container.
	 * @param propertyId
	 *            the id of the property.
	 * 
	 * @since 0.2.0
	 */
	public static void addNestedContainerProperty(
			AbstractBeanContainer<?, ?> container, String propertyId) {
		if (propertyId == null) {
			throw new NullPointerException("propertyId cannot be null.");
		}
		container.addNestedContainerProperty(propertyId);
	}

	/**
	 * Add a property to the given container using {@link PropertyMetadata}.
	 * 
	 * @see ContainerUtils#addShortcutContainerProperty(ShortcutBeanContainer,
	 *      String, String)
	 * 
	 * @param container
	 *            the container.
	 * @param propertyMetadata
	 *            the porperty metadata.
	 * 
	 * @since 0.2.0
	 */
	public static void addShortcutContainerProperty(AliasContainer container,
			PropertyMetadata propertyMetadata) {
		if (propertyMetadata.getPropertyAttribute() != null) {
			addShortcutContainerProperty(container,
					propertyMetadata.getPropertyName(),
					propertyMetadata.getPropertyAttribute());
		} else {
			addNestedContainerProperty((AbstractBeanContainer<?, ?>) container,
					propertyMetadata.getPropertyName());
		}
	}

	/**
	 * Add a property to the given container (ShortcutBeanContainer).
	 * 
	 * @param container
	 *            the container.
	 * @param propertyId
	 *            the id of the property.
	 * @param propertyPath
	 *            the path of the property.
	 * 
	 * @since 0.2.0
	 */
	public static void addShortcutContainerProperty(AliasContainer container,
			String propertyId, String propertyPath) {
		if (propertyId == null || propertyPath == null) {
			throw new NullPointerException(
					"propertyId and propertyPath cannot be null.");
		}
		container.addShortcutContainerProperty(propertyId, propertyPath);
	}

	/**
	 * Get the value of a property for the item identified by itemId for the
	 * given container.
	 * 
	 * @param container
	 * @param itemId
	 * @param property
	 * @return
	 */
	public static Object getValueOfProperty(Container container, Object itemId,
			Object propertyId) {
		if (container == null || itemId == null || propertyId == null
				|| !container.containsId(itemId)
				|| !containsPropertyId(container, propertyId)) {
			return null;
		}
		return container.getContainerProperty(itemId, propertyId).getValue();
	}

	public static Object getValueOfProperty(Item item, Object propertyId) {
		if (item == null || propertyId == null
				|| !containsPropertyId(item, propertyId)) {
			return null;
		}
		return item.getItemProperty(propertyId).getValue();
	}

	public static boolean containsPropertyId(Container container,
			Object propertyId) {
		return container.getContainerPropertyIds().contains(propertyId);
	}

	public static boolean containsPropertyId(Item item, Object propertyId) {
		return item.getItemPropertyIds().contains(propertyId);
	}
}
