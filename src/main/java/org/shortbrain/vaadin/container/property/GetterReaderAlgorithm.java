package org.shortbrain.vaadin.container.property;

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
