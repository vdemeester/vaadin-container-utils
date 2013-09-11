/*
 * Copyright 2012 Vincent Demeester<vincent+shortbrain@demeester.fr>.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.xgbi.vaadin.container;

import java.util.Collection;

import org.xgbi.vaadin.container.annotation.ContainerType;
import org.xgbi.vaadin.container.property.AnnotationReaderAlgorithm;
import org.xgbi.vaadin.container.property.PropertyReaderAlgorithm;

import com.vaadin.data.Container;
import com.vaadin.data.Container.Hierarchical;
import com.vaadin.data.Item;
import com.vaadin.data.util.AbstractBeanContainer;
import com.vaadin.data.util.AbstractBeanContainer.BeanIdResolver;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItemContainer;

/**
 * ContainerFactory abstract class that define methods to facilitate the creation of Containers from a list of objects using different
 * algorithms.
 * 
 * This Factory returns Container that are {@link com.vaadin.data.util.AbstractBeanContainer} implementation.
 * 
 * @author Vincent Demeester <vincent@shortbrain.org>
 * 
 * @param <IDTYPE>
 *            The type of the item identifier
 * @param <BEANTYPE>
 *            The type of the Bean
 * @since 0.2.0
 * 
 * @see ContainerFactory
 * @see com.vaadin.data.util.BeanContainer
 * @see com.vaadin.data.util.AbstractBeanContainer
 */
@SuppressWarnings("rawtypes")
public abstract class BeanContainerFactory<IDTYPE, BEANTYPE> implements IContainerFactory<BEANTYPE> {

    /**
     * Return a container of type BEAN from a collection of BEAN objects. It will update the given container if no null.
     * 
     * @param container
     *            container to be populated.
     * @param beans
     *            collection of beans.
     * @return a container of Beans.
     * 
     * @since 0.2.0
     */
    public abstract Container getContainerFromCollection(Container container, Collection<BEANTYPE> beans);

    /**
     * Return a container of type BEAN from a collection of BEAN objects. It will update the given container if no null.
     * 
     * The returned container will be of the given type (containerClass).
     * 
     * @param container
     *            container to be populated.
     * @param beans
     *            collection of beans.
     * @param containerClass
     *            type of the container to return.
     * @return a container of Beans.
     * 
     * @since 0.2.0
     */
    public abstract Container getContainerFromCollection(Container container, Collection<BEANTYPE> beans,
            Class<? extends Container> containerClass);

    /**
     * Return a container of type BEAN from a collection of BEAN objects.
     * 
     * The returned container will be of the given type (containerClass).
     * 
     * @param beans
     *            collection of beans.
     * @param containerClass
     *            type of the container to return.
     * @return a container of Beans.
     * 
     * @since 0.2.0
     */
    public abstract Container getContainerFromCollection(Collection<BEANTYPE> beans,
            Class<? extends Container> containerClass);

    /**
     * Return a new Item of type BEAN.
     * 
     * @param bean
     *            the bean.
     * 
     * @return a Item
     * @since 0.3.0
     */
    public abstract Item newItem(BEANTYPE bean);

    /**
     * Create a BeanContainerFactory of type T and idtype I using a default {@link AnnotationReaderAlgorithm} and the propertyId bean id
     * resolver. It uses {@link BeanItemContainer} implementation by default.
     * 
     * @see BeanContainerFactory#getByAlgorithm(Class, PropertyReaderAlgorithm, Class, HierarchicalBeanBuilder, BeanIdResolver)
     * 
     * @param beanClass
     *            type of the container.
     * @param containerType
     *            type of container (used by AnnotationReaderAlgorithm)
     * @return a BeanContainerFactory.
     * 
     * @since 0.2.0
     */
    public final static <I, T, A extends Enum> BeanContainerFactory<I, T> getByAnnotation(Class<? super T> beanClass,
            A containerType) {
        return getByAlgorithm(beanClass, new AnnotationReaderAlgorithm<A>(containerType), BeanItemContainer.class,
                (BeanIdResolver<I, T>) null);
    }

    /**
     * Create a BeanContainerFactory of type T and idtype I using a default {@link AnnotationReaderAlgorithm} and the propertyId bean id
     * resolver. It uses {@link BeanContainer} implemantation by default.
     * 
     * @see BeanContainerFactory#getByAlgorithm(Class, PropertyReaderAlgorithm, Class, HierarchicalBeanBuilder, String)
     * 
     * @param beanClass
     *            type of the container.
     * @param containerType
     *            type of container (used by AnnotationReaderAlgorithm)
     * @param propertyId
     *            the identifier of the property to use to find item identifiers.
     * @return a BeanContainerFactory.
     * 
     * @since 0.2.0
     */
    public final static <I, T, A extends Enum> BeanContainerFactory<I, T> getByAnnotation(Class<? super T> beanClass,
            A containerType, String propertyId) {
        return getByAlgorithm(beanClass, new AnnotationReaderAlgorithm<A>(containerType), BeanContainer.class,
                propertyId);
    }

    /**
     * Create a BeanContainerFactory of type T and idtype I using a default {@link AnnotationReaderAlgorithm} and the bean id resolver. It
     * uses {@link BeanContainer} implemantation by default.
     * 
     * @see BeanContainerFactory#getByAlgorithm(Class, PropertyReaderAlgorithm, Class, HierarchicalBeanBuilder, BeanIdResolver)
     * 
     * @param beanClass
     *            type of the container.
     * @param containerType
     *            type of container (used by AnnotationReaderAlgorithm)
     * @param beanIdResolver
     *            the beanIdResolver to use to find item identifiers.
     * @return a BeanContainerFactory.
     * 
     * @since 0.2.0
     */
    public final static <I, T, A extends Enum> BeanContainerFactory<I, T> getByAnnotation(Class<? super T> beanClass,
            A containerType, BeanIdResolver<I, T> beanIdResolver) {
        return getByAlgorithm(beanClass, new AnnotationReaderAlgorithm<A>(containerType), BeanContainer.class,
                beanIdResolver);
    }

    /**
     * Create a BeanContainerFactory of type T and idtype I using a default {@link AnnotationReaderAlgorithm} and the propertyId bean id
     * resolver.
     * 
     * @see BeanContainerFactory#getByAlgorithm(Class, PropertyReaderAlgorithm, Class, HierarchicalBeanBuilder, String)
     * 
     * @param beanClass
     *            type of the container.
     * @param containerType
     *            type of container (used by AnnotationReaderAlgorithm)
     * @param beanContainerType
     *            the type of {@link AbstractBeanContainer} to use.
     * @param propertyId
     *            the identifier of the property to use to find item identifiers.
     * @return a BeanContainerFactory.
     * 
     * @since 0.2.0
     */
    @SuppressWarnings("rawtypes")
    public final static <I, T, A extends Enum> BeanContainerFactory<I, T> getByAnnotation(Class<? super T> beanClass,
            A containerType, Class<? extends AbstractBeanContainer> beanContainerType, String propertyId) {
        return getByAlgorithm(beanClass, new AnnotationReaderAlgorithm<A>(containerType), beanContainerType, propertyId);
    }

    /**
     * Create a BeanContainerFactory of type T and idtype I using a default {@link AnnotationReaderAlgorithm} and the bean id resolver.
     * 
     * @see BeanContainerFactory#getByAlgorithm(Class, PropertyReaderAlgorithm, Class, HierarchicalBeanBuilder, BeanIdResolver)
     * 
     * @param beanClass
     *            type of the container.
     * @param containerType
     *            type of container (used by AnnotationReaderAlgorithm)
     * @param beanContainerType
     *            the type of {@link AbstractBeanContainer} to use.
     * @param beanIdResolver
     *            the beanIdResolver to use to find item identifiers.
     * @return a BeanContainerFactory.
     * 
     * @since 0.2.0
     */
    @SuppressWarnings("rawtypes")
    public final static <I, T, A extends Enum> BeanContainerFactory<I, T> getByAnnotation(Class<? super T> beanClass,
            A containerType, Class<? extends AbstractBeanContainer> beanContainerType,
            BeanIdResolver<I, T> beanIdResolver) {
        return getByAlgorithm(beanClass, new AnnotationReaderAlgorithm<A>(containerType), beanContainerType,
                beanIdResolver);
    }

    /**
     * Create a BeanContainerFactory of type T and idtype I using no {@link PropertyReaderAlgorithm} and the bean id resolver. It uses
     * {@link BeanItemContainer} implementation by default.
     * 
     * @see BeanContainerFactory#getByAlgorithm(Class, PropertyReaderAlgorithm, Class, HierarchicalBeanBuilder, BeanIdResolver)
     * 
     * @param beanClass
     *            type of the container.
     * @return a BeanContainerFactory.
     * 
     * @since 0.2.0
     */
    public final static <I, T> BeanContainerFactory<I, T> getSimple(Class<? super T> beanClass) {
        return getByAlgorithm(beanClass, null, BeanItemContainer.class, (BeanIdResolver<I, T>) null);
    }

    /**
     * Create a BeanContainerFactory of type T and idtype I using no {@link PropertyReaderAlgorithm} and the bean id resolver. It uses
     * {@link BeanContainer} implementation by default.
     * 
     * @see BeanContainerFactory#getByAlgorithm(Class, PropertyReaderAlgorithm, Class, HierarchicalBeanBuilder, BeanIdResolver)
     * 
     * @param beanClass
     *            type of the container.
     * @param beanIdResolver
     *            the beanIdResolver to use to find item identifiers.
     * @return a BeanContainerFactory.
     * 
     * @since 0.2.0
     */
    public final static <I, T> BeanContainerFactory<I, T> getSimple(Class<? super T> beanClass,
            BeanIdResolver<I, T> beanIdResolver) {
        return getByAlgorithm(beanClass, null, BeanContainer.class, beanIdResolver);
    }

    /**
     * Create a BeanContainerFactory of type T and idtype I using no {@link PropertyReaderAlgorithm} and the bean id resolver. It uses
     * {@link BeanContainer} implementation by default.
     * 
     * @see BeanContainerFactory#getByAlgorithm(Class, PropertyReaderAlgorithm, Class, HierarchicalBeanBuilder, BeanIdResolver)
     * 
     * @param beanClass
     *            type of the container.
     * @param propertyId
     *            the identifier of the property to use to find item identifiers.
     * @return a BeanContainerFactory.
     * 
     * @since 0.2.0
     */
    public final static <I, T> BeanContainerFactory<I, T> getSimple(Class<? super T> beanClass, String propertyId) {
        return getByAlgorithm(beanClass, null, BeanContainer.class, propertyId);
    }

    /**
     * Create a BeanContainerFactory of type T and idtype I using no {@link PropertyReaderAlgorithm} and the bean id resolver. It uses
     * {@link BeanContainer} implementation by default.
     * 
     * @see BeanContainerFactory#getByAlgorithm(Class, PropertyReaderAlgorithm, Class, HierarchicalBeanBuilder, BeanIdResolver)
     * 
     * @param beanClass
     *            type of the container.
     * @param beanContainerType
     *            the type of {@link AbstractBeanContainer} to use.
     * @param beanIdResolver
     *            the beanIdResolver to use to find item identifiers.
     * @return a BeanContainerFactory.
     * 
     * @since 0.2.0
     */
    @SuppressWarnings("rawtypes")
    public final static <I, T> BeanContainerFactory<I, T> getSimple(Class<? super T> beanClass,
            Class<? extends AbstractBeanContainer> beanContainerType, BeanIdResolver<I, T> beanIdResolver) {
        return getByAlgorithm(beanClass, null, beanContainerType, beanIdResolver);
    }

    /**
     * Create a BeanContainerFactory of type T and idtype I using no {@link PropertyReaderAlgorithm} and the bean id resolver. It uses
     * {@link BeanContainer} implementation by default.
     * 
     * @see BeanContainerFactory#getByAlgorithm(Class, PropertyReaderAlgorithm, Class, HierarchicalBeanBuilder, BeanIdResolver)
     * 
     * @param beanClass
     *            type of the container.
     * @param beanContainerType
     *            the type of {@link AbstractBeanContainer} to use.
     * @param propertyId
     *            the identifier of the property to use to find item identifiers.
     * @return a BeanContainerFactory.
     * 
     * @since 0.2.0
     */
    @SuppressWarnings("rawtypes")
    public final static <I, T> BeanContainerFactory<I, T> getSimple(Class<? super T> beanClass,
            Class<? extends AbstractBeanContainer> beanContainerType, String propertyId) {
        return getByAlgorithm(beanClass, null, beanContainerType, propertyId);
    }

    /**
     * Create a BeanContainerFactory of type T and idtype I using a {@link PropertyReaderAlgorithm} and the bean id resolver.
     * 
     * @param beanClass
     *            type of the container.
     * @param algorithm
     *            PropertyReaderAlgorithm to use.
     * @param beanContainerType
     *            the type of {@link AbstractBeanContainer} to use.
     * @param beanBuilder
     *            the hierarchical beanBuilder to use to build a {@link Hierarchical}
     * @param beanIdResolver
     *            the beanIdResolver to use to find item identifiers.
     * @return a BeanContainerFactory.
     * 
     * @since 0.2.0
     */
    @SuppressWarnings("rawtypes")
    public final static <I, T> BeanContainerFactory<I, T> getByAlgorithm(Class<? super T> beanClass,
            PropertyReaderAlgorithm algorithm, Class<? extends AbstractBeanContainer> beanContainerType,
            BeanIdResolver<I, T> beanIdResolver) {
        return new AbstractBeanContainerFactory<I, T>(beanClass, algorithm, beanContainerType, beanIdResolver) {
        };
    }

    /**
     * Create a BeanContainerFactory of type T and idtype I using a {@link PropertyReaderAlgorithm} and the propertyId bean id resolver.
     * 
     * @param beanClass
     *            type of the container.
     * @param algorithm
     *            PropertyReaderAlgorithm to use.
     * @param beanContainerType
     *            the type of {@link AbstractBeanContainer} to use.
     * @param beanBuilder
     *            the hierarchical beanBuilder to use to build a {@link Hierarchical}
     * @param propertyId
     *            the identifier of the property to use to find item identifiers.
     * @return a BeanContainerFactory.
     * 
     * @since 0.2.0
     */
    @SuppressWarnings("rawtypes")
    public final static <I, T> BeanContainerFactory<I, T> getByAlgorithm(Class<? super T> beanClass,
            PropertyReaderAlgorithm algorithm, Class<? extends AbstractBeanContainer> beanContainerType,
            String propertyId) {
        return new AbstractBeanContainerFactory<I, T>(beanClass, algorithm, beanContainerType, propertyId) {
        };
    }

}
