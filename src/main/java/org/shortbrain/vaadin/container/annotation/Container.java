package org.shortbrain.vaadin.container.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Container annotation to be added to a Bean.
 * 
 * @author Vincent Demeester <vincent@demeester.fr>
 * 
 */
@Target({ TYPE })
@Retention(RUNTIME)
public @interface Container {

	/**
	 * Property list
	 * 
	 * @return a list of {@link Property}.
	 */
	Property[] properties() default {};
}
