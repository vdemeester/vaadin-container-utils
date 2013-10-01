/*
 * Copyright 2012 Vincent Demeester<vincent+shortbrain@demeester.fr>.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.xgbi.vaadin.container.annotation.reader;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.xgbi.vaadin.container.annotation.Container;
import org.xgbi.vaadin.container.annotation.ContainerType;
import org.xgbi.vaadin.container.annotation.Property;
import org.xgbi.vaadin.container.property.PropertyMetadata;

/**
 * Reader of Entity to generate containers metadatas
 * 
 * @author Vincent Demeester <vincent@demeester.fr>
 * 
 */
public class ContainerBeanAnnotationReader
		extends
		BeanAbstractAnnotationReader<Map<ContainerType, List<PropertyMetadata>>> {

	private ContainerBeanAnnotationReader(Class<?> originalEntityClass,
			Class<?> entityClass) {
		super(originalEntityClass, entityClass);
		setMetadatas(new HashMap<ContainerType, List<PropertyMetadata>>());
	}

	protected void process() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException,
			InstantiationException, SecurityException, NoSuchFieldException {
		Container container = getBeanClass().getAnnotation(Container.class);
		for (Property property : container.properties()) {
			processProperty(property);
		}
	}

	private void processProperty(Property property)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, InstantiationException, SecurityException,
			NoSuchFieldException {
		for (ContainerType containerType : property.types()) {
			if (!getMetadatas().containsKey(containerType)) {
				getMetadatas().put(containerType,
						new LinkedList<PropertyMetadata>());
			}
			String propertyName = property.name();
			String propertyAttribute = property.attribute();
			if (property.attribute().equals("")) {
				propertyAttribute = property.name();
			}
			Class<?> propertyClass = getPropertyType(propertyAttribute);
			PropertyMetadata propertyMetadata = new PropertyMetadata(
					propertyName, propertyClass, null, propertyAttribute);
			getMetadatas().get(containerType).add(propertyMetadata);
		}
	}

	public static Map<ContainerType, List<PropertyMetadata>> getMetadataByContainerType(
			Class<?> originalEntityClass) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException,
			InstantiationException, SecurityException, NoSuchFieldException {
		Map<ContainerType, List<PropertyMetadata>> ret = null;
		Class<?> entityClass = getAnnotatedClass(originalEntityClass,
				Container.class);
		if (entityClass != null) {
			ContainerBeanAnnotationReader reader = new ContainerBeanAnnotationReader(
					originalEntityClass, entityClass);
			reader.process();
			ret = reader.getMetadatas();
		}
		return ret;
	}

}
