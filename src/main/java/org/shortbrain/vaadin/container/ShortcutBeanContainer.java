package org.shortbrain.vaadin.container;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.ShortcutPropertyDescriptor;

/**
 * An extension of the {@link BeanContainer}, which adds shorcuts properties.
 * 
 * @author Vincent Demeester <vincent@shortbrain.org>
 * 
 * @param <IDTYPE>
 *            The type of the item identifier
 * @param <BEANTYPE>
 *            The type of the Bean
 * 
 * @since 0.2.0
 * 
 * @see BeanContainer
 */
public class ShortcutBeanContainer<IDTYPE, BEANTYPE> extends com.vaadin.data.util.BeanContainer<IDTYPE, BEANTYPE>
        implements IShortcutBeanContainer {

    private static final long serialVersionUID = 2865701930991415312L;

    /**
     * Create a {@link ShortcutBeanContainer}
     * 
     * @param type
     *            The type of the Bean
     */
    public ShortcutBeanContainer(Class<? super BEANTYPE> type) {
        super(type);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public boolean addShortcutContainerProperty(String propertyId, String propertyPath) {
        return addContainerProperty(propertyId, new ShortcutPropertyDescriptor(propertyId, propertyPath, getBeanType()));
    }
}
