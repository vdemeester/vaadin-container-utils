package org.xgbi.vaadin.container;

import static org.xgbi.vaadin.container.ContainerUtils.addContainerProperty;

import java.io.InvalidClassException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConstructorUtils;
import org.xgbi.vaadin.container.property.PropertyMetadata;
import org.xgbi.vaadin.container.property.PropertyReaderAlgorithm;

import com.vaadin.data.Container;
import com.vaadin.data.Container.Filterable;
import com.vaadin.data.Container.Hierarchical;
import com.vaadin.data.Item;
import com.vaadin.data.util.AbstractBeanContainer;
import com.vaadin.data.util.AbstractBeanContainer.BeanIdResolver;
import com.vaadin.data.util.AliasPropertyDescriptor;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.VaadinPropertyDescriptor;

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
public abstract class AbstractBeanContainerFactory<IDTYPE, BEANTYPE> extends BeanContainerFactory<IDTYPE, BEANTYPE> {

    /**
     * Type of the bean.
     */
    private final Class<? super BEANTYPE> beanClass;
    private final Class<? extends AbstractBeanContainer> beanContainerType;

    private Constructor<? extends AbstractBeanContainer> containerConstructor;

    private Method itemGropertyDescriptorsMethod;
    private Constructor<? extends BeanItem> itemBeanItemConstructor;

    private String propertyId;

    private BeanIdResolver<IDTYPE, BEANTYPE> beanIdResolver;

    /**
     * The property reader algorithm.
     */
    private final PropertyReaderAlgorithm propertyReaderAlgorithm;

    /**
     * Create an {@link AbstractBeanContainerFactory}.
     * 
     * @param beanClass
     *            the type of the Bean.
     * @param propertyReaderAlgorithm
     *            the algorithm to be used to get properties.
     * @param beanContainerType
     *            the type of beanContainer to instantiate
     * @param propertyId
     *            the identifier of the property to use to find item identifiers.
     */
    public AbstractBeanContainerFactory(Class<? super BEANTYPE> beanClass,
            PropertyReaderAlgorithm propertyReaderAlgorithm, Class<? extends AbstractBeanContainer> beanContainerType,
            String propertyId) {
        this(beanClass, propertyReaderAlgorithm, beanContainerType, propertyId, null);
    }

    /**
     * Create an {@link AbstractBeanContainerFactory}.
     * 
     * @param beanClass
     *            the type of the Bean.
     * @param propertyReaderAlgorithm
     *            the algorithm to be used to get properties.
     * @param beanContainerType
     *            the type of beanContainer to instantiate.
     * @param beanIdResolver
     *            the beanIdResolver to use to find item identifier.
     */
    public AbstractBeanContainerFactory(Class<? super BEANTYPE> beanClass,
            PropertyReaderAlgorithm propertyReaderAlgorithm, Class<? extends AbstractBeanContainer> beanContainerType,
            BeanIdResolver<IDTYPE, BEANTYPE> beanIdResolver) {
        this(beanClass, propertyReaderAlgorithm, beanContainerType, null, beanIdResolver);
    }

    /**
     * Create an {@link AbstractBeanContainerFactory}.
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
    private AbstractBeanContainerFactory(Class<? super BEANTYPE> beanClass,
            PropertyReaderAlgorithm propertyReaderAlgorithm, Class<? extends AbstractBeanContainer> beanContainerType,
            String propertyId, BeanIdResolver<IDTYPE, BEANTYPE> beanIdResolver) {
        this.beanClass = beanClass;
        this.beanContainerType = beanContainerType;
        this.containerConstructor = ConstructorUtils.getAccessibleConstructor(beanContainerType, Class.class);
        this.propertyReaderAlgorithm = propertyReaderAlgorithm;
        this.propertyId = propertyId;
        this.beanIdResolver = beanIdResolver;
        try {
            Method method = BeanItem.class.getDeclaredMethod("getPropertyDescriptors", Class.class);
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            itemGropertyDescriptorsMethod = method;
            Constructor<? extends BeanItem> constructor = BeanItem.class.getDeclaredConstructor(Object.class, Map.class);
            if (!constructor.isAccessible()) {
                constructor.setAccessible(true);
            }
            itemBeanItemConstructor = constructor;
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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

    @Override
    public Item newItem(BEANTYPE bean) {
        Item item = null;
        if (itemGropertyDescriptorsMethod != null) {
            try {
                // 1. Get propertyDescriptors
                Map<String, VaadinPropertyDescriptor<BEANTYPE>> propertyDescriptors = (Map<String, VaadinPropertyDescriptor<BEANTYPE>>) itemGropertyDescriptorsMethod
                        .invoke(null, beanClass);
                // 2. Update propertyDescriptors
                if (propertyReaderAlgorithm != null) {
                    List<PropertyMetadata> properties = propertyReaderAlgorithm.getProperties(beanClass);
                    for (PropertyMetadata property : properties) {
                        if (AliasContainer.class.isAssignableFrom(beanContainerType)) {
                            if (property.getPropertyAttribute() != null) {
                                propertyDescriptors.put(
                                        property.getPropertyName(),
                                        new AliasPropertyDescriptor<BEANTYPE>(property.getPropertyName(), property
                                                .getPropertyAttribute(), (Class<BEANTYPE>) beanClass));
                            }
                        }
                    }
                }
                // 3. Instantiate beanItem
                item = itemBeanItemConstructor.newInstance(bean, propertyDescriptors);
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            item = new BeanItem<BEANTYPE>(bean);
        }
        return item;
    }

    private Container initContainer(Class<? extends Container> containerClass) throws InvalidClassException,
            IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Container container = containerConstructor.newInstance(beanClass);
        if (container instanceof BeanContainer<?, ?>) {
            if (beanIdResolver != null) {
                ((BeanContainer<IDTYPE, BEANTYPE>) container).setBeanIdResolver(beanIdResolver);
            } else if (propertyId != null) {
                ((BeanContainer<IDTYPE, BEANTYPE>) container).setBeanIdProperty(propertyId);
            }
        } else if (container instanceof BeanItemContainer<?> || container instanceof AliasBeanItemContainer<?>) {
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
