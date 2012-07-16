package org.shortbrain.vaadin.container.annotation.reader;

import java.lang.annotation.Annotation;

/**
 * Generic annotation reader.
 * 
 * This currently only work on annotation for Type.
 * 
 * @author Vincent Demeester <vincent@demeester.fr>
 * @version 0.4.0
 * 
 */
public abstract class BeanAbstractAnnotationReader<T> {

	private final Class<?> originalEntityClass;
	private final Class<?> entityClass;
	private T metadata;

	public BeanAbstractAnnotationReader(Class<?> originalEntityClass,
			Class<?> entityClass) {
		this.originalEntityClass = originalEntityClass;
		this.entityClass = entityClass;
	}

	/**
	 * @return the originalEntityClass
	 */
	protected Class<?> getOriginalEntityClass() {
		return originalEntityClass;
	}

	/**
	 * @return the entityClass
	 */
	protected Class<?> getEntityClass() {
		return entityClass;
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
	 */
	protected Class<?> getPropertyType(String propertyAttribute)
			throws SecurityException, NoSuchFieldException {
		Class<?> ret = null;
		if (propertyAttribute.contains(".")) {
			String fieldName = propertyAttribute.split("\\.")[0];
			String subFieldName = propertyAttribute.split("\\.")[1];
			Class<?> fieldClass = getOriginalEntityClass().getDeclaredField(
					fieldName).getType();
			ret = fieldClass.getDeclaredField(subFieldName).getType();
		} else {
			ret = getOriginalEntityClass().getDeclaredField(propertyAttribute)
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
	 */
	public static Class<?> getAnnotatedClass(Class<?> entityClass,
			Class<? extends Annotation> annotationClass) {
		Class<?> ret = null;
		if (!entityClass.isAnnotationPresent(annotationClass)) {
			// On remonte dans la hierarchie jusqu'à Object
			if (entityClass.getSuperclass() != Object.class) {
				ret = getAnnotatedClass(entityClass.getSuperclass(),
						annotationClass);
			}
		} else {
			ret = entityClass;
		}
		return ret;
	}

}
