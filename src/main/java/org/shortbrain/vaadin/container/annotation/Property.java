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
	 * @return the name of the property.
	 */
	String name();

	/**
	 * Type of container this property should be added
	 * 
	 * @return the types the property should be assoicated with.
	 */
	ContainerType[] types() default { ContainerType.RESUME,
			ContainerType.EXTENDED };

	/**
	 * Attribute to get the value from an object.
	 * 
	 * Form : {attribute}.{attribute}
	 * 
	 * @return the attribute to lookup.
	 */
	String attribute() default "";

}
