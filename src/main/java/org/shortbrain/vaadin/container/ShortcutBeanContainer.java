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
public class ShortcutBeanContainer<IDTYPE, BEANTYPE> extends com.vaadin.data.util.BeanContainer<IDTYPE, BEANTYPE> {

    private static final long serialVersionUID = 2865701930991415312L;

    public ShortcutBeanContainer(Class<? super BEANTYPE> type) {
        super(type);
    }

    /**
     * Adds a shortcut container property for the container, e.g. "lieuVille" for "lieu.ville"
     * 
     * @param propertyId
     * @param propertyPath
     * @return true if the property was added
     */
    public boolean addShortcutContainerProperty(String propertyId, String propertyPath) {
        return addContainerProperty(propertyId, new ShortcutPropertyDescriptor(propertyId, propertyPath, getBeanType()));
    }
}
