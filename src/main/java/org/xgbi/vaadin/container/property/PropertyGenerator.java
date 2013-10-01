package org.xgbi.vaadin.container.property;

import com.vaadin.data.Property;

/**
 * Interface that defines methodes to implement for the property generators.
 * 
 * @author Vincent Demeester <vincent@sbr.io>
 * 
 */
public interface PropertyGenerator<TYPE, BEAN> {

	/**
	 * Get the property.
	 * 
	 * @param bean
	 * @return
	 */
	public Property getProperty(BEAN bean);

	/**
	 * Get the type of the property.
	 * 
	 * @return
	 */
	public Class<? super TYPE> getType();
}
