package org.shortbrain.vaadin.container.property;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.shortbrain.vaadin.container.annotation.Container;
import org.shortbrain.vaadin.container.annotation.ContainerType;
import static org.shortbrain.vaadin.container.annotation.reader.ContainerEntityAnnotationReader.getMetadataByContainerType;

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

	public AnnotationReaderAlgorithm(ContainerType containerType) {
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
		return containersMeta.get(containerType);
	}
}
