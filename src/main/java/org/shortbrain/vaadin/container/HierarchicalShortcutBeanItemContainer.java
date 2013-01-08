package org.shortbrain.vaadin.container;

import org.shortbrain.vaadin.container.property.HierarchicalBeanBuilder;

import com.vaadin.data.util.AbstractHierarchicalBeanContainer;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.ContainerHierarchicalWrapper;

/**
 * An extension of {@link ShortcutBeanItemContainer} that is {@link com.vaadin.data.Container.Hierarchical}.
 * 
 * <p>
 * Behind the scene it uses {@link ContainerHierarchicalWrapper}.
 * </p>
 * 
 * @author Vincent Demeester <vincent@shortbrain.org>
 * 
 * @param <BEANTYPE>
 *            The type of the Bean
 * 
 * @since 0.2.0
 * 
 * @see AbstractHierarchicalBeanContainer
 * @see BeanContainer
 * @see ContainerHierarchicalWrapper
 */
public class HierarchicalShortcutBeanItemContainer<BEANTYPE> extends
        AbstractHierarchicalBeanContainer<BEANTYPE, BEANTYPE> implements IShortcutBeanContainer {

    private static final long serialVersionUID = 3022071979651114886L;

    /**
     * Create a {@link HierarchicalShortcutBeanItemContainer} with the given IDTYPE and BEANTYPE.
     * 
     * @param type
     *            The type of the Bean
     */
    public HierarchicalShortcutBeanItemContainer(Class<BEANTYPE> type) {
        super(type, new ShortcutBeanItemContainer<BEANTYPE>(type));
    }

    /**
     * Create a {@link HierarchicalShortcutBeanItemContainer} with the given IDTYPE and BEANTYPE.
     * 
     * @param type
     *            The type of the Bean
     * @param hierarchicalBeanBuilder
     *            builder for the {@link com.vaadin.data.Container.Hierarchical}
     */
    public HierarchicalShortcutBeanItemContainer(Class<BEANTYPE> type,
            HierarchicalBeanBuilder<BEANTYPE, BEANTYPE> hierarchicalBeanBuilder) {
        this(type);
        setHierarchicalBeanBuilder(hierarchicalBeanBuilder);
    }

    @Override
    public void setBeanIdProperty(Object propertyId) {
        throw new UnsupportedOperationException("BeanItemContainer always uses an IdentityBeanIdResolver");
    }

    @Override
    public boolean addShortcutContainerProperty(String propertyId, String propertyPath) {
        return ((ShortcutBeanItemContainer<BEANTYPE>) getContainer()).addShortcutContainerProperty(propertyId,
                propertyPath);
    }
}
