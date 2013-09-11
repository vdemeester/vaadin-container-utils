package org.xgbi.vaadin.container;

import java.util.Collection;

import com.vaadin.data.util.AbstractBeanContainer;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;

/**
 * An extension of the {@link BeanContainer}, which adds shorcuts properties.
 * 
 * @author Vincent Demeester <vincent@shortbrain.org>
 * 
 * @param <BEANTYPE>
 *            The type of the Bean
 * 
 * @since 0.2.0
 * 
 * @see AliasBeanContainer
 * @see BeanItemContainer
 */
public class AliasBeanItemContainer<BEANTYPE> extends AliasBeanContainer<BEANTYPE, BEANTYPE> implements AliasContainer {

    private static final long serialVersionUID = 4221589659162741891L;

    /**
     * Bean identity resolver that returns the bean itself as its item identifier.
     * 
     * This corresponds to the old behavior of {@link BeanItemContainer}, and requires suitable (identity-based) equals() and hashCode()
     * methods on the beans.
     * 
     * @param <BT>
     * 
     * @since 6.5
     */
    private static class IdentityBeanIdResolver<BT> implements BeanIdResolver<BT, BT> {

        private static final long serialVersionUID = 1L;

        public BT getIdForBean(BT bean) {
            return bean;
        }

    }

    /**
     * Constructs a {@code ShortcutBeanItemContainer}.
     * 
     * @param type
     *            the type of the beans that will be added to the container.
     * @throws IllegalArgumentException
     *             If {@code type} is null
     */
    public AliasBeanItemContainer(Class<? super BEANTYPE> type) {
        super(type);
        super.setBeanIdResolver(new IdentityBeanIdResolver<BEANTYPE>());
    }

    /**
     * Constructs a {@code ShortcutBeanItemContainer} and adds the given beans to it.
     * 
     * @param type
     *            the type of the beans that will be added to the container.
     * @param collection
     *            a {@link Collection} of beans (can be empty or null).
     * @throws IllegalArgumentException
     *             If {@code type} is null
     */
    public AliasBeanItemContainer(Class<? super BEANTYPE> type, Collection<? extends BEANTYPE> collection)
            throws IllegalArgumentException {
        super(type);
        super.setBeanIdResolver(new IdentityBeanIdResolver<BEANTYPE>());

        if (collection != null) {
            addAll(collection);
        }
    }

    /**
     * Adds all the beans from a {@link Collection} in one go. More efficient than adding them one by one.
     * 
     * @param collection
     *            The collection of beans to add. Must not be null.
     */
    @Override
    public void addAll(Collection<? extends BEANTYPE> collection) {
        super.addAll(collection);
    }

    /**
     * Adds the bean after the given bean.
     * 
     * The bean is used both as the item contents and as the item identifier.
     * 
     * @param previousItemId
     *            the bean (of type BT) after which to add newItemId
     * @param newItemId
     *            the bean (of type BT) to add (not null)
     * 
     * @see com.vaadin.data.Container.Ordered#addItemAfter(Object, Object)
     */
    @Override
    @SuppressWarnings("unchecked")
    public BeanItem<BEANTYPE> addItemAfter(Object previousItemId, Object newItemId) throws IllegalArgumentException {
        return super.addBeanAfter((BEANTYPE) previousItemId, (BEANTYPE) newItemId);
    }

    /**
     * Adds a new bean at the given index.
     * 
     * The bean is used both as the item contents and as the item identifier.
     * 
     * @param index
     *            Index at which the bean should be added.
     * @param newItemId
     *            The bean to add to the container.
     * @return Returns the new BeanItem or null if the operation fails.
     */
    @Override
    @SuppressWarnings("unchecked")
    public BeanItem<BEANTYPE> addItemAt(int index, Object newItemId) throws IllegalArgumentException {
        return super.addBeanAt(index, (BEANTYPE) newItemId);
    }

    /**
     * Adds the bean to the Container.
     * 
     * The bean is used both as the item contents and as the item identifier.
     * 
     * @see com.vaadin.data.Container#addItem(Object)
     */
    @Override
    @SuppressWarnings("unchecked")
    public BeanItem<BEANTYPE> addItem(Object itemId) {
        return super.addBean((BEANTYPE) itemId);
    }

    /**
     * Adds the bean to the Container.
     * 
     * The bean is used both as the item contents and as the item identifier.
     * 
     * @see com.vaadin.data.Container#addItem(Object)
     */
    @Override
    public BeanItem<BEANTYPE> addBean(BEANTYPE bean) {
        return addItem(bean);
    }

    /**
     * Unsupported in BeanItemContainer.
     */
    @Override
    public void setBeanIdResolver(AbstractBeanContainer.BeanIdResolver<BEANTYPE, BEANTYPE> beanIdResolver)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException("BeanItemContainer always uses an IdentityBeanIdResolver");
    }
}
