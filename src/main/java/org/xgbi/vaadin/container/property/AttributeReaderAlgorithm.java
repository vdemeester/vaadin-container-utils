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

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of {@link PropertyReaderAlgorithm} that looks for attribute of
 * the beanClass
 * 
 * @author Vincent Demeester <vincent@demeester.fr>
 * 
 */
public class AttributeReaderAlgorithm implements PropertyReaderAlgorithm {

	/**
	 * Constant for default valuer of withSuper attribute.
	 */
	private static final boolean DEFAULT_WITHSUPER = true;
	private static final List<String> DEFAULT_IGNORED = Arrays
			.asList(new String[] { "serialVersionUID" });

	/**
	 * List of attribute to ignore.
	 */
	private final Collection<String> ignoredAttributes;
	private final boolean withSuper;

	/**
	 * Create an {@link AttributeReaderAlgorithm} with default ignored
	 * attributes ("serialVersionUID") and with <code>super</code> class lookup.
	 */
	public AttributeReaderAlgorithm() {
		this(DEFAULT_IGNORED, DEFAULT_WITHSUPER);
	}

	/**
	 * Create an {@link AttributeReaderAlgorithm} with default ignored
	 * attributes ("serialVersionUID").
	 * 
	 * @param withSuper
	 *            look up for <code>super</class> class.
	 */
	public AttributeReaderAlgorithm(boolean withSuper) {
		this(DEFAULT_IGNORED, withSuper);
	}

	/**
	 * Create an {@link AttributeReaderAlgorithm} with <code>super</code> class
	 * lookup.
	 * 
	 * @param ignoredAttributes
	 *            list of attributes to ignore.
	 */
	public AttributeReaderAlgorithm(Collection<String> ignoredAttributes) {
		this(ignoredAttributes, DEFAULT_WITHSUPER);
	}

	/**
	 * Create an {@link AttributeReaderAlgorithm}.
	 * 
	 * @param ignoredAttributes
	 *            list of attributes to ignore.
	 * @param withSuper
	 *            look up for <code>super</class> class.
	 */
	public AttributeReaderAlgorithm(Collection<String> ignoredAttributes,
			boolean withSuper) {
		this.ignoredAttributes = ignoredAttributes;
		this.withSuper = withSuper;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * If the ignoredAttributes attribute is null, we won't fail, it will be
	 * just as if it is an empty List.
	 * 
	 * @throw {@link IllegalArgumentException} if beanClass is null.
	 */
	@Override
	public List<PropertyMetadata> getProperties(Class<?> beanClass) {
		if (beanClass == null) {
			throw new IllegalArgumentException("beanClass cannot be null.");
		}
		List<PropertyMetadata> metadatas = new LinkedList<PropertyMetadata>();
		List<Field> fields = new LinkedList<Field>();
		fields.addAll(Arrays.asList(beanClass.getDeclaredFields()));
		if (withSuper) {
			Class<?> superClass = beanClass.getSuperclass();
			while (superClass != Object.class) {
				fields.addAll(Arrays.asList(superClass.getDeclaredFields()));
				superClass = superClass.getSuperclass();
			}
		}
		for (Field field : fields) {
			if (ignoredAttributes == null
					|| !ignoredAttributes.contains(field.getName())) {
				String propertyName = field.getName();
				Class<?> propertyClass = field.getType();
				String propertyAttribute = field.getName();
				PropertyMetadata metadata = new PropertyMetadata(propertyName,
						propertyClass, null, propertyAttribute);
				metadatas.add(metadata);
			}
		}
		return metadatas;
	}
}
