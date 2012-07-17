package org.shortbrain.vaadin.container.annotation.reader;

import java.lang.annotation.Annotation;

/**
 * Generic annotation reader.
 * 
 * This currently only work on annotation for Type.
 * 
 * @author Vincent Demeester <vincent@demeester.fr>
 * @param <T>
 *            type of the Annotation.
 * 
 */
public abstract class BeanAbstractAnnotationReader<T> {

	private final Class<?> originalBeanClass;
	private final Class<?> beanClass;
	private T metadata;

	/**
	 * Creates a BeanAbstractAnnotationReader.
	 * 
	 * @param originalBeanClass
	 *            the original bean type.
	 * @param beanClass
	 *            the <em>annotated</em> bean type (might be a parent).
	 * @throws IllegalArgumentException
	 *             if the originalBeanClass or beanClass is null.
	 */
	public BeanAbstractAnnotationReader(Class<?> originalBeanClass,
			Class<?> beanClass) {
		if (originalBeanClass == null || beanClass == null) {
			throw new IllegalArgumentException(
					"originalBeanClass and beanClass cannot be null.");
		}
		this.originalBeanClass = originalBeanClass;
		this.beanClass = beanClass;
	}

	/**
	 * @return the originalEntityClass
	 */
	protected Class<?> getOriginalBeanClass() {
		return originalBeanClass;
	}

	/**
	 * @return the entityClass
	 */
	protected Class<?> getBeanClass() {
		return beanClass;
	}

	public void setMetadatas(T metadata) {
		this.metadata = metadata;
	}

	public T getMetadatas() {
		return metadata;
	}

	/**
	 * Get the type of the given propertyAttribute
	 * 
	 * @param propertyAttribute
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 *             if the field is not found or if propertyAttribute is null.
	 */
	protected Class<?> getPropertyType(String propertyAttribute)
			throws SecurityException, NoSuchFieldException {
		if (propertyAttribute == null) {
			throw new NoSuchFieldException("no null field.");
		}
		Class<?> ret = null;
		if (propertyAttribute.contains(".")) {
			String fieldName = propertyAttribute.split("\\.")[0];
			String subFieldName = propertyAttribute.split("\\.")[1];
			Class<?> fieldClass = getOriginalBeanClass().getDeclaredField(
					fieldName).getType();
			ret = fieldClass.getDeclaredField(subFieldName).getType();
		} else {
			ret = getOriginalBeanClass().getDeclaredField(propertyAttribute)
					.getType();
		}
		return ret;
	}

	/**
	 * Get the annotated type by walking in superclass to find the annotation
	 * 
	 * @param entityClass
	 *            The type that should be annotated
	 * @param annotationClass
	 *            the type of the annotation
	 * @return the "real" type annotated, either entityClass or a parent.
	 * @throws IllegalArgumentException
	 *             if entityClass or annotationClass are null or if entityClass
	 *             (and its parent) is not annotated with annotationClass.
	 */
	public static Class<?> getAnnotatedClass(Class<?> entityClass,
			Class<? extends Annotation> annotationClass) {
		if (entityClass == null || annotationClass == null) {
			throw new IllegalArgumentException(
					"entityClass and annotationClass cannot be null.");
		}
		Class<?> ret = null;
		if (!entityClass.isAnnotationPresent(annotationClass)) {
			// On remonte dans la hierarchie jusqu'à Object
			if (entityClass.getSuperclass() != Object.class) {
				ret = getAnnotatedClass(entityClass.getSuperclass(),
						annotationClass);
			}
			if (ret == null) {
				throw new IllegalArgumentException(
						"entityClass and its super classes are not annotated with "
								+ annotationClass.getSimpleName() + ".");
			}
		} else {
			ret = entityClass;
		}
		return ret;
	}

}
