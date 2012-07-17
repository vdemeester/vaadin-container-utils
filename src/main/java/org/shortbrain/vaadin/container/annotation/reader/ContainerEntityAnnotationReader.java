package org.shortbrain.vaadin.container.annotation.reader;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.shortbrain.vaadin.container.annotation.Container;
import org.shortbrain.vaadin.container.annotation.ContainerType;
import org.shortbrain.vaadin.container.annotation.Property;
import org.shortbrain.vaadin.container.property.PropertyMetadata;

/**
 * Reader of Entity to generate containers metadatas
 * 
 * @author Vincent Demeester <vincent@demeester.fr>
 * 
 */
public class ContainerEntityAnnotationReader
		extends
		BeanAbstractAnnotationReader<Map<ContainerType, List<PropertyMetadata>>> {

	private ContainerEntityAnnotationReader(Class<?> originalEntityClass,
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
			ContainerEntityAnnotationReader reader = new ContainerEntityAnnotationReader(
					originalEntityClass, entityClass);
			reader.process();
			ret = reader.getMetadatas();
		}
		return ret;
	}

}
