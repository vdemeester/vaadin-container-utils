package org.xgbi.vaadin.container.descriptor;

import org.xgbi.vaadin.container.property.PropertyGenerator;

import com.vaadin.data.Property;
import com.vaadin.data.util.VaadinPropertyDescriptor;

public class GeneratedPropertyDescriptor<BEANTYPE> implements
		VaadinPropertyDescriptor<BEANTYPE> {

	private static final long serialVersionUID = 5626414812205817073L;
	private final PropertyGenerator<?, BEANTYPE> generator;
	private final String propertyId;

	public GeneratedPropertyDescriptor(String propertyId,
			PropertyGenerator<?, BEANTYPE> generator) {
		this.generator = generator;
		this.propertyId = propertyId;
	}

	@Override
	public String getName() {
		return propertyId;
	}

	@Override
	public Class<?> getPropertyType() {
		return generator.getType();
	}

	@Override
	public Property createProperty(BEANTYPE bean) {
		return generator.getProperty(bean);
	}

}
