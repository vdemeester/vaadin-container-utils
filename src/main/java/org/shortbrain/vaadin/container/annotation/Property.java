package org.shortbrain.vaadin.container.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Property annotation to be used with {@link Container}
 * 
 * @author vincent
 * 
 */
@Target({ TYPE })
@Retention(RUNTIME)
public @interface Property {

	/**
	 * Name of the property
	 * 
	 * @return
	 */
	String name();

	/**
	 * Type of container this property should be added
	 * 
	 * @return
	 */
	ContainerType[] types() default { ContainerType.RESUME,
			ContainerType.EXTENDED };

	/**
	 * Attribute to get the value from an object.
	 * 
	 * Form : {attribute}.{attribute}
	 * 
	 * @return
	 */
	String attribute() default "";

}
