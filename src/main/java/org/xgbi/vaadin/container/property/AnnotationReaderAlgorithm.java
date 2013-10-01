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
package org.xgbi.vaadin.container.property;

import static org.xgbi.vaadin.container.annotation.reader.ContainerBeanAnnotationReader.getMetadataByContainerType;

import java.util.List;
import java.util.Map;

import org.xgbi.vaadin.container.annotation.Container;
import org.xgbi.vaadin.container.annotation.ContainerType;

/**
 * Implementation of {@link PropertyReaderAlgorithm} that looks for annotation
 * {@link Container}.
 * 
 * @author Vincent Demeester <vincent@demeester.fr>
 * 
 */
public class AnnotationReaderAlgorithm<T extends Enum> implements PropertyReaderAlgorithm {

	/**
	 * Container type.
	 */
	private final T containerType;
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
	public AnnotationReaderAlgorithm(T containerType) {
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
			} catch (Exception e) {
				// The beanClass passed is not annotated with Container
				throw new IllegalArgumentException(
						"the beanClass (or parent class) has to be annotated witch @Container.", e);
			}
		}
		if (containersMeta == null) {
			// The beanClass passed is not annotated with Container
			throw new IllegalArgumentException(
					"the beanClass (or parent class) has to be annotated witch @Container.");
		}
		return containersMeta.get(containerType);
	}
}
