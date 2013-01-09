package org.shortbrain.vaadin.container;

import java.io.InvalidClassException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.ConstructorUtils;
import org.shortbrain.vaadin.container.property.HierarchicalBeanBuilder;
import org.shortbrain.vaadin.container.property.PropertyMetadata;
import org.shortbrain.vaadin.container.property.PropertyReaderAlgorithm;

import com.vaadin.data.Container;
import com.vaadin.data.Container.Filterable;
import com.vaadin.data.Container.Hierarchical;
import com.vaadin.data.util.AbstractBeanContainer;
import com.vaadin.data.util.AbstractBeanContainer.BeanIdResolver;
import com.vaadin.data.util.AbstractHierarchicalBeanContainer;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItemContainer;

import static org.shortbrain.vaadin.container.ContainerUtils.addContainerProperty;

/**
 * Default abstract implementation of {@link BeanContainerFactory}
 * 
 * @author Vincent Demeester <vincent@shortbrain.org>
 * 
 * @param <IDTYPE>
 *            The type of the item identifier
 * @param <BEANTYPE>
 *            The type of the Bean
 * @since 0.2.0
 * 
 * @see BeanContainerFactory
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class AbstractHierarchicalBeanContainerFactory<IDTYPE, BEANTYPE> extends HierarchicalBeanContainerFactory<IDTYPE, BEANTYPE> {

    /**
     * Type of the bean.
     */
    private final Class<? super BEANTYPE> beanClass;

    private Constructor<? extends AbstractBeanContainer> containerConstructor;

    private String propertyId;

    private BeanIdResolver<IDTYPE, BEANTYPE> beanIdResolver;

    private HierarchicalBeanBuilder<IDTYPE, BEANTYPE> beanBuilder;

    /**
     * The property reader algorithm.
     */
    private final PropertyReaderAlgorithm propertyReaderAlgorithm;

    /**
     * Create an {@link AbstractHierarchicalBeanContainerFactory}.
     * 
     * @param beanClass
     *            the type of the Bean.
     * @param propertyReaderAlgorithm
     *            the algorithm to be used to get properties.
     * @param beanContainerType
     *            the type of beanContainer to instantiate
     * @param propertyId
     *            the identifier of the property to use to find item identifiers.
     * @param beanBuilder
     *            the hierarchical beanBuilder to use to build a {@link Hierarchical}
     */
    public AbstractHierarchicalBeanContainerFactory(Class<? super BEANTYPE> beanClass,
            PropertyReaderAlgorithm propertyReaderAlgorithm, Class<? extends AbstractBeanContainer> beanContainerType,
            String propertyId, HierarchicalBeanBuilder<IDTYPE, BEANTYPE> beanBuilder) {
        this(beanClass, propertyReaderAlgorithm, beanContainerType, propertyId, null, beanBuilder);
    }

    /**
     * Create an {@link AbstractHierarchicalBeanContainerFactory}.
     * 
     * @param beanClass
     *            the type of the Bean.
     * @param propertyReaderAlgorithm
     *            the algorithm to be used to get properties.
     * @param beanContainerType
     *            the type of beanContainer to instantiate.
     * @param beanIdResolver
     *            the beanIdResolver to use to find item identifier.
     * @param beanBuilder
     *            the hierarchical beanBuilder to use to build a {@link Hierarchical}
     */
    public AbstractHierarchicalBeanContainerFactory(Class<? super BEANTYPE> beanClass,
            PropertyReaderAlgorithm propertyReaderAlgorithm, Class<? extends AbstractBeanContainer> beanContainerType,
            BeanIdResolver<IDTYPE, BEANTYPE> beanIdResolver, HierarchicalBeanBuilder<IDTYPE, BEANTYPE> beanBuilder) {
        this(beanClass, propertyReaderAlgorithm, beanContainerType, null, beanIdResolver, beanBuilder);
    }

    /**
     * Create an {@link AbstractHierarchicalBeanContainerFactory}.
     * 
     * This is a private constructor to be used by the others.
     * 
     * @param beanClass
     *            the type of the Bean.
     * @param propertyReaderAlgorithm
     *            the algorithm to be used to get properties.
     * @param beanContainerType
     *            the type of beanContainer to instantiate.
     * @param propertyId
     *            the identifier of the property to use to find item identifiers.
     * @param beanIdResolver
     *            the beanIdResolver to use to find item identifier.
     * @param beanBuilder
     *            the hierarchical beanBuilder to use to build a {@link Hierarchical}
     */
    private AbstractHierarchicalBeanContainerFactory(Class<? super BEANTYPE> beanClass,
            PropertyReaderAlgorithm propertyReaderAlgorithm, Class<? extends AbstractBeanContainer> beanContainerType,
            String propertyId, BeanIdResolver<IDTYPE, BEANTYPE> beanIdResolver,
            HierarchicalBeanBuilder<IDTYPE, BEANTYPE> beanBuilder) {
        this.beanClass = beanClass;
        this.containerConstructor = ConstructorUtils.getAccessibleConstructor(beanContainerType, Class.class);
        this.propertyReaderAlgorithm = propertyReaderAlgorithm;
        this.propertyId = propertyId;
        this.beanIdResolver = beanIdResolver;
        this.beanBuilder = beanBuilder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Container getContainerFromCollection(Container container, Collection<BEANTYPE> beans) {
        Class<? extends Container> containerClass = (container != null) ? container.getClass() : Filterable.class;
        return getContainerFromCollection(container, beans, containerClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Container getContainerFromCollection(Collection<BEANTYPE> beans, Class<? extends Container> containerClass) {
        return getContainerFromCollection(null, beans, containerClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Container getContainerFromCollection(Container container, Collection<BEANTYPE> beans,
            Class<? extends Container> containerClass) {
        // Instansiate it
        // FIXME How to handle Exception ?
        try {
            if (container == null || !(container instanceof AbstractBeanContainer<?, ?>)) {
                container = initContainer(containerClass);
            }
            updateProperties(container);
            if (container.removeAllItems()) {
                // Re-add them
                if (container instanceof BeanContainer<?, ?>) {
                    ((BeanContainer<IDTYPE, BEANTYPE>) container).addAll(beans);
                } else if (container instanceof BeanItemContainer<?>) {
                    ((BeanItemContainer<BEANTYPE>) container).addAll(beans);
                } else {
                    // Should never happen
                    throw new InvalidClassException("container class is not supported.");
                }
            }
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidClassException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return container;
    }

    private Container initContainer(Class<? extends Container> containerClass) throws InvalidClassException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
        // FIXME Simplify everything (HierarchicalBeanContainerFactory instead)
        Container container = containerConstructor.newInstance(beanClass);
        if (beanBuilder != null) {
            ((AbstractHierarchicalBeanContainer)container).setHierarchicalBeanBuilder(beanBuilder);
        }
        if (container instanceof BeanContainer<?, ?>) {
            if (beanIdResolver != null) {
                ((BeanContainer<IDTYPE, BEANTYPE>) container).setBeanIdResolver(beanIdResolver);
            } else if (propertyId != null) {
                ((BeanContainer<IDTYPE, BEANTYPE>) container).setBeanIdProperty(propertyId);
            }
        } else if (container instanceof BeanItemContainer<?> || container instanceof ShortcutBeanItemContainer<?>) {
            // Nothing to do
        } else {
            // Should never happen
            throw new InvalidClassException("container class is not supported.");
        }
        return container;
    }

    /**
     * Create or update properties of the given container.
     * 
     * @param container
     *            the container to be updated.
     */
    private List<PropertyMetadata> updateProperties(Container container) {
        if (propertyReaderAlgorithm != null) {
            List<PropertyMetadata> properties = propertyReaderAlgorithm.getProperties(beanClass);
            Collection<?> containerProperties = container.getContainerPropertyIds();
            for (PropertyMetadata property : properties) {
                if (!containerProperties.contains(property.getPropertyName())) {
                    addContainerProperty(container, property);
                }
            }
            return properties;
        }
        return null;
    }

}
