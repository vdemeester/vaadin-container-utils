package org.shortbrain.vaadin.container;

import org.shortbrain.vaadin.container.property.HierarchicalBeanBuilder;

import com.vaadin.data.util.AbstractHierarchicalBeanContainer;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.ContainerHierarchicalWrapper;

/**
 * An extension of {@link ShortcutBeanContainer} that is {@link com.vaadin.data.Container.Hierarchical}.
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
 * @see com.vaadin.data.Container.Hierarchical
 */
public class HierarchicalShortcutBeanContainer<IDTYPE, BEANTYPE> extends
        AbstractHierarchicalBeanContainer<IDTYPE, BEANTYPE> implements IShortcutBeanContainer {

    private static final long serialVersionUID = -4643557075868038241L;

    /**
     * Create a {@link HierarchicalShortcutBeanContainer} with the given IDTYPE and BEANTYPE.
     * 
     * @param type
     *            The type of the Bean
     */
    public HierarchicalShortcutBeanContainer(Class<BEANTYPE> type) {
        super(type, new ShortcutBeanContainer<IDTYPE, BEANTYPE>(type));
    }

    /**
     * Create a {@link HierarchicalShortcutBeanContainer} with the given IDTYPE and BEANTYPE.
     * 
     * @param type
     *            The type of the Bean
     * @param hierarchicalBeanBuilder
     *            The builder for {@link com.vaadin.data.Container.Hierarchical}
     */
    public HierarchicalShortcutBeanContainer(Class<BEANTYPE> type,
            HierarchicalBeanBuilder<IDTYPE, BEANTYPE> hierarchicalBeanBuilder) {
        this(type);
        setHierarchicalBeanBuilder(hierarchicalBeanBuilder);
    }

    @Override
    public void setBeanIdProperty(Object propertyId) {
        ((ShortcutBeanContainer<IDTYPE, BEANTYPE>) getContainer()).setBeanIdProperty(propertyId);
    }

    @Override
    public boolean addShortcutContainerProperty(String propertyId, String propertyPath) {
        return ((ShortcutBeanContainer<IDTYPE, BEANTYPE>) getContainer()).addShortcutContainerProperty(propertyId, propertyPath);
    }
}
