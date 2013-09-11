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

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of {@link PropertyReaderAlgorithm} that looks for method
 * (getter) of the beanClass.
 * 
 * @author Vincent Demeester <vincent@demeester.fr>
 * 
 */
public class GetterReaderAlgorithm implements PropertyReaderAlgorithm {

	public GetterReaderAlgorithm() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PropertyMetadata> getProperties(Class<?> beanClass) {
		if (beanClass == null) {
			throw new IllegalArgumentException("beanClass cannot be null.");
		}
		List<PropertyMetadata> metadatas = new LinkedList<PropertyMetadata>();
		for (Method method : beanClass.getDeclaredMethods()) {
			if (Modifier.isPublic(method.getModifiers())
					&& method.getName().startsWith("get")) {
				String propertyName = method.getName().substring(3);
				Class<?> propertyClass = method.getReturnType();
				String propertyAttribute = propertyName;
				PropertyMetadata metadata = new PropertyMetadata(propertyName,
						propertyClass, null, propertyAttribute);
				metadatas.add(metadata);
			}
		}
		return metadatas;
	}
}
