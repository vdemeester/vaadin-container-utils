package com.vaadin.data.util;

import org.xgbi.vaadin.container.property.HierarchicalBeanBuilder;

import com.vaadin.data.Container.Hierarchical;

/**
 * An extension of {@link BeanContainer} that is {@link Hierarchical}.
 * 
 * <p>
 * Behind the scene it uses {@link ContainerHierarchicalWrapper}.
 * </p>
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
 * @see AbstractHierarchicalBeanContainer
 * @see BeanContainer
 * @see ContainerHierarchicalWrapper
 * @see Hierarchical
 */
public class HierarchicalBeanContainer<IDTYPE, BEANTYPE> extends AbstractHierarchicalBeanContainer<IDTYPE, BEANTYPE> {

    private static final long serialVersionUID = 2360235181091674900L;

    /**
     * Create a {@link HierarchicalBeanContainer} with the given IDTYPE and BEANTYPE.
     * 
     * @param type
     *            The type of the Bean
     */
    public HierarchicalBeanContainer(Class<BEANTYPE> type) {
        super(type, new BeanContainer<IDTYPE, BEANTYPE>(type));
    }

    /**
     * Create a {@link HierarchicalBeanContainer} with the given IDTYPE and BEANTYPE.
     * 
     * @param type
     *            The type of the Bean
     * @param hierarchicalBeanBuilder
     *            The builder for {@link Hierarchical}
     */
    public HierarchicalBeanContainer(Class<BEANTYPE> type, HierarchicalBeanBuilder<IDTYPE, BEANTYPE> hierarchicalBeanBuilder) {
        this(type);
        setHierarchicalBeanBuilder(hierarchicalBeanBuilder);
    }

    @Override
    public void setBeanIdProperty(Object propertyId) {
        ((BeanContainer<IDTYPE, BEANTYPE>) getContainer()).setBeanIdProperty(propertyId);
    }

}
