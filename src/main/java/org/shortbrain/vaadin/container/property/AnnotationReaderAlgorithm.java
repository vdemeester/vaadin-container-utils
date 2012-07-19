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
package org.shortbrain.vaadin.container.property;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.shortbrain.vaadin.container.annotation.Container;
import org.shortbrain.vaadin.container.annotation.ContainerType;
import static org.shortbrain.vaadin.container.annotation.reader.ContainerBeanAnnotationReader.getMetadataByContainerType;

/**
 * Implementation of {@link PropertyReaderAlgorithm} that looks for annotation
 * {@link Container}.
 * 
 * @author Vincent Demeester <vincent@demeester.fr>
 * 
 */
public class AnnotationReaderAlgorithm implements PropertyReaderAlgorithm {

	/**
	 * Container type.
	 */
	private final ContainerType containerType;
	private Map<ContainerType, List<PropertyMetadata>> containersMeta;

	/**
	 * Create an {@link AnnotationReaderAlgorithm} with a {@link ContainerType}
	 * specified.
	 * 
	 * If the containerType argument is null, it will throw an
	 * {@link IllegalArgumentException}
	 * 
	 * @param containerType
	 * @throws IllegalArgumentException
	 */
	public AnnotationReaderAlgorithm(ContainerType containerType) {
		if (containerType == null) {
			throw new IllegalArgumentException("containerType cannot be null.");
		}
		this.containerType = containerType;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * FIXME: Handle Exception better.
	 */
	@Override
	public List<PropertyMetadata> getProperties(Class<?> beanClass) {
		if (containersMeta == null) {
			try {
				containersMeta = getMetadataByContainerType(beanClass);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
			}
		}
		if (containersMeta == null) {
			// The beanClass passed is not annotated with Container
			throw new IllegalArgumentException(
					"the beanClass has to be annotated witch @Container.");
		}
		return containersMeta.get(containerType);
	}
}
