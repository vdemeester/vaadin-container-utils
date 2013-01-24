package com.vaadin.data.util;

import org.shortbrain.vaadin.container.property.HierarchicalBeanBuilder;

/**
 * An extension of {@link BeanItemContainer} that is {@link com.vaadin.data.Container.Hierarchical}.
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
 * @see com.vaadin.data.Container.Hierarchical
 */
public class HierarchicalBeanItemContainer<BEANTYPE> extends AbstractHierarchicalBeanContainer<BEANTYPE, BEANTYPE> {

    private static final long serialVersionUID = 2707281313111154765L;

    /**
     * Create a {@link HierarchicalBeanContainer} with the given IDTYPE and BEANTYPE.
     * 
     * @param type
     *            The type of the Bean
     */
    public HierarchicalBeanItemContainer(Class<BEANTYPE> type) {
        super(type, new BeanItemContainer<BEANTYPE>(type));
    }

    /**
     * Create a {@link HierarchicalBeanContainer} with the given IDTYPE and BEANTYPE.
     * 
     * @param type
     *            The type of the Bean
     * @param hierarchicalBeanBuilder
     *            The builder for {@link com.vaadin.data.Container.Hierarchical}
     */
    public HierarchicalBeanItemContainer(Class<BEANTYPE> type,
            HierarchicalBeanBuilder<BEANTYPE, BEANTYPE> hierarchicalBeanBuilder) {
        this(type);
        setHierarchicalBeanBuilder(hierarchicalBeanBuilder);
    }

    @Override
    public void setBeanIdProperty(Object propertyId) {
        throw new UnsupportedOperationException("BeanItemContainer always uses an IdentityBeanIdResolver");
    }

}
