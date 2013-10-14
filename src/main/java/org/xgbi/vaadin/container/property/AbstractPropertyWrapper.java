package org.xgbi.vaadin.container.property;

public abstract class AbstractPropertyWrapper implements PropertyWrapper {

	private final Object propertyId;

	protected AbstractPropertyWrapper(Object propertyId) {
		this.propertyId = propertyId;
	}

	@Override
	public Object getPropertyId() {
		return this.propertyId;
	}

}
