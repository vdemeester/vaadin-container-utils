package com.vaadin.data.util;

import com.vaadin.data.Property;

/**
 * Property descriptor that is able to create shortcut property instances for a bean (nested or not).
 * 
 * The property path is specified in the dotted notation, e.g. "address.street", and can contain multiple levels of nesting. The property
 * name can be anything (but a String).
 * 
 * @param <BT>
 *            bean type
 * 
 * @author Vincent Demeester <vincent.demeester@xgbi.fr>
 * 
 * @since 0.2.0
 * 
 * @see NestedPropertyDescriptor
 */
public class AliasPropertyDescriptor<BT> implements VaadinPropertyDescriptor<BT> {

    private static final long serialVersionUID = -724628501672480236L;

    private final String name;
    private final String path;
    private final Class<?> propertyType;

    /**
     * Create a {@link AliasPropertyDescriptor}.
     * 
     * @param name
     *            The given name of the property
     * @param path
     *            The path of the real property
     * @param beanType
     *            the type of bean to look for this property
     */
    public AliasPropertyDescriptor(String name, String path, Class<BT> beanType) {
        this.name = name;
        this.path = path;
        NestedMethodProperty property = new NestedMethodProperty(beanType, path);
        this.propertyType = property.getType();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<?> getPropertyType() {
        return propertyType;
    }

    @Override
    public Property createProperty(BT bean) {
        return new NestedMethodProperty(bean, path);
    }

}
