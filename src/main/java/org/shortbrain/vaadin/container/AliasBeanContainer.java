package org.shortbrain.vaadin.container;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.AliasPropertyDescriptor;

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
public class AliasBeanContainer<IDTYPE, BEANTYPE> extends com.vaadin.data.util.BeanContainer<IDTYPE, BEANTYPE>
        implements AliasContainer {

    private static final long serialVersionUID = 2865701930991415312L;

    /**
     * Create a {@link AliasBeanContainer}
     * 
     * @param type
     *            The type of the Bean
     */
    public AliasBeanContainer(Class<? super BEANTYPE> type) {
        super(type);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public boolean addShortcutContainerProperty(String propertyId, String propertyPath) {
        return addContainerProperty(propertyId, new AliasPropertyDescriptor(propertyId, propertyPath, getBeanType()));
    }
}
