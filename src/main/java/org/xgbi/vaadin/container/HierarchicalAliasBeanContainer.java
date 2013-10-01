package org.xgbi.vaadin.container;

import org.xgbi.vaadin.container.descriptor.GeneratedPropertyDescriptor;
import org.xgbi.vaadin.container.property.HierarchicalBeanBuilder;
import org.xgbi.vaadin.container.property.PropertyGenerator;

import com.vaadin.data.util.AbstractHierarchicalBeanContainer;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.ContainerHierarchicalWrapper;

/**
 * An extension of {@link AliasBeanContainer} that is {@link com.vaadin.data.Container.Hierarchical}.
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
public class HierarchicalAliasBeanContainer<IDTYPE, BEANTYPE> extends
        AbstractHierarchicalBeanContainer<IDTYPE, BEANTYPE> implements AliasContainer, GeneratedPropertiesContainer<BEANTYPE> {

    private static final long serialVersionUID = -4643557075868038241L;

    /**
     * Create a {@link HierarchicalAliasBeanContainer} with the given IDTYPE and BEANTYPE.
     * 
     * @param type
     *            The type of the Bean
     */
    public HierarchicalAliasBeanContainer(Class<BEANTYPE> type) {
        super(type, new AliasBeanContainer<IDTYPE, BEANTYPE>(type));
    }

    /**
     * Create a {@link HierarchicalAliasBeanContainer} with the given IDTYPE and BEANTYPE.
     * 
     * @param type
     *            The type of the Bean
     * @param hierarchicalBeanBuilder
     *            The builder for {@link com.vaadin.data.Container.Hierarchical}
     */
    public HierarchicalAliasBeanContainer(Class<BEANTYPE> type,
            HierarchicalBeanBuilder<IDTYPE, BEANTYPE> hierarchicalBeanBuilder) {
        this(type);
        setHierarchicalBeanBuilder(hierarchicalBeanBuilder);
    }

    @Override
    public void setBeanIdProperty(Object propertyId) {
        ((AliasBeanContainer<IDTYPE, BEANTYPE>) getContainer()).setBeanIdProperty(propertyId);
    }

    @Override
    public boolean addShortcutContainerProperty(String propertyId, String propertyPath) {
        return ((AliasBeanContainer<IDTYPE, BEANTYPE>) getContainer()).addShortcutContainerProperty(propertyId, propertyPath);
    }
    
	@Override
	public boolean addGeneratedContainerProperty(String propertyId,
			PropertyGenerator<?, BEANTYPE> generator) {
		return addContainerProperty(propertyId, new GeneratedPropertyDescriptor<BEANTYPE>(propertyId, generator));
	}
}
