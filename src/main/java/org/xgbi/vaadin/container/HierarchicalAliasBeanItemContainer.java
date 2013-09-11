package org.xgbi.vaadin.container;

import org.xgbi.vaadin.container.property.HierarchicalBeanBuilder;

import com.vaadin.data.util.AbstractHierarchicalBeanContainer;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.ContainerHierarchicalWrapper;

/**
 * An extension of {@link AliasBeanItemContainer} that is {@link com.vaadin.data.Container.Hierarchical}.
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
public class HierarchicalAliasBeanItemContainer<BEANTYPE> extends
        AbstractHierarchicalBeanContainer<BEANTYPE, BEANTYPE> implements AliasContainer {

    private static final long serialVersionUID = 3022071979651114886L;

    /**
     * Create a {@link HierarchicalAliasBeanItemContainer} with the given IDTYPE and BEANTYPE.
     * 
     * @param type
     *            The type of the Bean
     */
    public HierarchicalAliasBeanItemContainer(Class<BEANTYPE> type) {
        super(type, new AliasBeanItemContainer<BEANTYPE>(type));
    }

    /**
     * Create a {@link HierarchicalAliasBeanItemContainer} with the given IDTYPE and BEANTYPE.
     * 
     * @param type
     *            The type of the Bean
     * @param hierarchicalBeanBuilder
     *            builder for the {@link com.vaadin.data.Container.Hierarchical}
     */
    public HierarchicalAliasBeanItemContainer(Class<BEANTYPE> type,
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
        return ((AliasBeanItemContainer<BEANTYPE>) getContainer()).addShortcutContainerProperty(propertyId,
                propertyPath);
    }

    @Override
    public boolean removeContainerProperty(String propertyId) {
        return ((AliasBeanItemContainer<BEANTYPE>) getContainer()).removeContainerProperty(propertyId);
    }
    
}
