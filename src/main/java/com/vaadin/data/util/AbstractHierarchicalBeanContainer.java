package com.vaadin.data.util;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.shortbrain.vaadin.container.AliasBeanContainer;
import org.shortbrain.vaadin.container.property.HierarchicalBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.filter.UnsupportedFilterException;

/**
 * An extension of {@link BeanContainer} that is {@link Hierarchical}. This is
 * the Abstract class that define common stuff for all BeanContainer type (
 * {@link BeanContainer}, {@link BeanItemContainer}, {@link AliasBeanContainer},
 * ...)
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
 * @see BeanContainer
 * @see ContainerHierarchicalWrapper
 * @see Hierarchical
 */
public abstract class AbstractHierarchicalBeanContainer<IDTYPE, BEANTYPE>
		extends BeanContainer<IDTYPE, BEANTYPE> implements
		Container.Hierarchical, Container.ItemSetChangeListener,
		Container.PropertySetChangeListener {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory
			.getLogger(AbstractHierarchicalBeanContainer.class);

	private Field modelField;

	/**
	 * The {@link Hierarchical} wrapper.
	 */
	private ContainerHierarchicalWrapper wrapper;

	/**
	 * The wrapped {@link Container} ({@link BeanContainer})
	 */
	private AbstractBeanContainer<IDTYPE, BEANTYPE> container;

	private HierarchicalBeanBuilder<IDTYPE, BEANTYPE> hierarchicalBeanBuilder;

	/**
	 * Create a {@link HierarchicalBeanContainer} with the given IDTYPE and
	 * BEANTYPE.
	 * 
	 * @param type
	 *            The type of the Bean
	 * @param container
	 *            The container to be wrapped
	 */
	public AbstractHierarchicalBeanContainer(Class<BEANTYPE> type,
			AbstractBeanContainer<IDTYPE, BEANTYPE> container) {
		super(type);
		this.container = container;
		wrapper = new ContainerHierarchicalWrapper(container);
		wrapper.addListener((Container.ItemSetChangeListener) this);
		wrapper.addListener((Container.PropertySetChangeListener) this);
		try {
			modelField = AbstractBeanContainer.class.getDeclaredField("model");
			modelField.setAccessible(true);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	@Override
	public void containerPropertySetChange(PropertySetChangeEvent event) {
		log.trace("PropertySet event {}", event);
	}

	@Override
	public void containerItemSetChange(ItemSetChangeEvent event) {
		// Working only if hierarchicalPropertyId is defined
		if (hierarchicalBeanBuilder != null) {
			// Map the parent and their children
			Map<IDTYPE, Collection<IDTYPE>> mapParent = new HashMap<IDTYPE, Collection<IDTYPE>>();
			// Walk the container
			for (IDTYPE itemId : container.getItemIds()) {
				BEANTYPE bean = container.getItem(itemId).getBean();
				Collection<IDTYPE> ids = hierarchicalBeanBuilder
						.getChildren(bean);
				if (ids != null && !ids.isEmpty()) {
					mapParent.put(itemId, ids);
				}
			}
			// Walk the map and set parents
			for (Entry<IDTYPE, Collection<IDTYPE>> entry : mapParent.entrySet()) {
				IDTYPE parent = entry.getKey();
				wrapper.setChildrenAllowed(parent, true);
				for (IDTYPE id : entry.getValue()) {
					wrapper.setParent(id, parent);
				}
			}
		}
		log.trace("ItemSet event {}", event);
	}

	// Getter and Setter
	/**
	 * Set the {@link HierarchicalBeanBuilder}
	 * 
	 * @param hierarchicalBeanBuilder
	 */
	public void setHierarchicalBeanBuilder(
			HierarchicalBeanBuilder<IDTYPE, BEANTYPE> hierarchicalBeanBuilder) {
		this.hierarchicalBeanBuilder = hierarchicalBeanBuilder;
	}

	/**
	 * Get the {@link ContainerHierarchicalWrapper} wrapper
	 * 
	 * @return the wrapper
	 */
	protected ContainerHierarchicalWrapper getWrapper() {
		return wrapper;
	}

	/**
	 * Get the wrapped container
	 * 
	 * @return the container
	 */
	protected AbstractBeanContainer<IDTYPE, BEANTYPE> getContainer() {
		return container;
	}

	/**
	 * Get the {@link HierarchicalBeanBuilder}
	 * 
	 * @return the {@link HierarchicalBeanBuilder}
	 */
	public HierarchicalBeanBuilder<IDTYPE, BEANTYPE> getHierarchicalBeanBuilder() {
		return hierarchicalBeanBuilder;
	}

	// wrapper forwarding
	@Override
	public Collection<?> getChildren(Object itemId) {
		return wrapper.getChildren(itemId);
	}

	@Override
	public Object getParent(Object itemId) {
		return wrapper.getParent(itemId);
	}

	@Override
	public Collection<?> rootItemIds() {
		return wrapper.rootItemIds();
	}

	@Override
	public boolean setParent(Object itemId, Object newParentId)
			throws UnsupportedOperationException {
		return wrapper.setParent(itemId, newParentId);
	}

	@Override
	public boolean areChildrenAllowed(Object itemId) {
		return wrapper.areChildrenAllowed(itemId);
	}

	@Override
	public boolean setChildrenAllowed(Object itemId, boolean areChildrenAllowed)
			throws UnsupportedOperationException {
		return wrapper.setChildrenAllowed(itemId, areChildrenAllowed);
	}

	@Override
	public boolean isRoot(Object itemId) {
		return wrapper.isRoot(itemId);
	}

	@Override
	public boolean hasChildren(Object itemId) {
		return wrapper.hasChildren(itemId);
	}

	// container forwarding
	@Override
	public BeanItem<BEANTYPE> addItem(IDTYPE itemId, BEANTYPE bean) {
		return container.addItem(itemId, bean);
	}

	@Override
	public BeanItem<BEANTYPE> addItemAfter(IDTYPE previousItemId,
			IDTYPE newItemId, BEANTYPE bean) {
		return container.addItemAfter(previousItemId, newItemId, bean);
	}

	@Override
	public BeanItem<BEANTYPE> addItemAt(int index, IDTYPE newItemId,
			BEANTYPE bean) {
		return container.addItemAt(index, newItemId, bean);
	}

	@Override
	public abstract void setBeanIdProperty(Object propertyId);

	@Override
	public void setBeanIdResolver(
			com.vaadin.data.util.AbstractBeanContainer.BeanIdResolver<IDTYPE, BEANTYPE> beanIdResolver) {
		container.setBeanIdResolver(beanIdResolver);
	}

	@Override
	public BeanItem<BEANTYPE> addBean(BEANTYPE bean)
			throws IllegalStateException, IllegalArgumentException {
		return container.addBean(bean);
	}

	@Override
	public BeanItem<BEANTYPE> addBeanAfter(IDTYPE previousItemId, BEANTYPE bean)
			throws IllegalStateException, IllegalArgumentException {
		return container.addBeanAfter(previousItemId, bean);
	}

	@Override
	public BeanItem<BEANTYPE> addBeanAt(int index, BEANTYPE bean)
			throws IllegalStateException, IllegalArgumentException {
		return container.addBeanAt(index, bean);
	}

	@Override
	public void addAll(Collection<? extends BEANTYPE> collection)
			throws IllegalStateException {
		container.addAll(collection);
	}

	@Override
	public Class<?> getType(Object propertyId) {
		return container.getType(propertyId);
	}

	@Override
	public Class<? super BEANTYPE> getBeanType() {
		return container.getBeanType();
	}

	@Override
	public Collection<String> getContainerPropertyIds() {
		return container.getContainerPropertyIds();
	}

	@Override
	public boolean removeAllItems() {
		return container.removeAllItems();
	}

	@Override
	public BeanItem<BEANTYPE> getItem(Object itemId) {
		return container.getItem(itemId);
	}

	@Override
	public List<IDTYPE> getItemIds() {
		return container.getItemIds();
	}

	@Override
	public Property getContainerProperty(Object itemId, Object propertyId) {
		return container.getContainerProperty(itemId, propertyId);
	}

	@Override
	public boolean removeItem(Object itemId) {
		return container.removeItem(itemId);
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		container.valueChange(event);
	}

	@Override
	public void addContainerFilter(Object propertyId, String filterString,
			boolean ignoreCase, boolean onlyMatchPrefix) {
		container.addContainerFilter(propertyId, filterString, ignoreCase,
				onlyMatchPrefix);
	}

	@Override
	public void removeAllContainerFilters() {
		container.removeAllContainerFilters();
	}

	@Override
	public void removeContainerFilters(Object propertyId) {
		container.removeContainerFilters(propertyId);
	}

	@Override
	public void addContainerFilter(Filter filter)
			throws UnsupportedFilterException {
		container.addContainerFilter(filter);
	}

	@Override
	public void removeContainerFilter(Filter filter) {
		container.removeContainerFilter(filter);
	}

	@Override
	public Collection<?> getSortableContainerPropertyIds() {
		return container.getSortableContainerPropertyIds();
	}

	@Override
	public void sort(Object[] propertyId, boolean[] ascending) {
		container.sort(propertyId, ascending);
	}

	@Override
	public ItemSorter getItemSorter() {
		return container.getItemSorter();
	}

	@Override
	public void setItemSorter(ItemSorter itemSorter) {
		container.setItemSorter(itemSorter);
	}

	@Override
	public com.vaadin.data.util.AbstractBeanContainer.BeanIdResolver<IDTYPE, BEANTYPE> getBeanIdResolver() {
		return container.getBeanIdResolver();
	}

	@Override
	public void addListener(PropertySetChangeListener listener) {
		container.addListener(listener);
	}

	@Override
	public void removeListener(PropertySetChangeListener listener) {
		container.removeListener(listener);
	}

	@Override
	public boolean addNestedContainerProperty(String propertyId) {
		return container.addNestedContainerProperty(propertyId);
	}

	@Override
	public boolean removeContainerProperty(Object propertyId)
			throws UnsupportedOperationException {
		return container.removeContainerProperty(propertyId);
	}

	@Override
	public int size() {
		return container.size();
	}

	@Override
	public boolean containsId(Object itemId) {
		return container.containsId(itemId);
	}

	@Override
	public IDTYPE nextItemId(Object itemId) {
		return container.nextItemId(itemId);
	}

	@Override
	public IDTYPE prevItemId(Object itemId) {
		return container.prevItemId(itemId);
	}

	@Override
	public IDTYPE firstItemId() {
		return container.firstItemId();
	}

	@Override
	public IDTYPE lastItemId() {
		return container.lastItemId();
	}

	@Override
	public boolean isFirstId(Object itemId) {
		return container.isFirstId(itemId);
	}

	@Override
	public boolean isLastId(Object itemId) {
		return container.isLastId(itemId);
	}

	@Override
	public IDTYPE getIdByIndex(int index) {
		return container.getIdByIndex(index);
	}

	@Override
	public int indexOfId(Object itemId) {
		return container.indexOfId(itemId);
	}

	@Override
	public Object addItemAt(int index) throws UnsupportedOperationException {
		return container.addItemAt(index);
	}

	@Override
	public Item addItemAt(int index, Object newItemId)
			throws UnsupportedOperationException {
		return container.addItemAt(index, newItemId);
	}

	@Override
	public Object addItemAfter(Object previousItemId)
			throws UnsupportedOperationException {
		return container.addItemAfter(previousItemId);
	}

	@Override
	public Item addItemAfter(Object previousItemId, Object newItemId)
			throws UnsupportedOperationException {
		return container.addItemAfter(previousItemId, newItemId);
	}

	@Override
	public Item addItem(Object itemId) throws UnsupportedOperationException {
		return container.addItem(itemId);
	}

	@Override
	public Object addItem() throws UnsupportedOperationException {
		return container.addItem();
	}

	@Override
	public void addListener(ItemSetChangeListener listener) {
		container.addListener(listener);
	}

	@Override
	public void removeListener(ItemSetChangeListener listener) {
		container.removeListener(listener);
	}

	@Override
	public Collection<?> getListeners(Class<?> eventType) {
		return container.getListeners(eventType);
	}

	public boolean removeContainerProperty(String propertyId) {
		// FIXME handle things better.
		if (modelField != null) {
			try {
				Map<String, VaadinPropertyDescriptor<BEANTYPE>> model = (Map<String, VaadinPropertyDescriptor<BEANTYPE>>) modelField
						.get(this);
				if (model.containsKey(propertyId)) {
					model.remove(propertyId);
				}
				return true;
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
}
