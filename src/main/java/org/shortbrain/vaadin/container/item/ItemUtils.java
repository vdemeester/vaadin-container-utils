package org.shortbrain.vaadin.container.item;


import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;

public class ItemUtils {
	
	public static boolean isBeanItem(Item item) {
		if (item instanceof BeanItem) {
			return true;
		} else if (item instanceof WrappedItem) {
			return (isBeanItem(((WrappedItem) item).getItem()));
		}
		return false;
	}
	
	public static BeanItem<?> getBeanItem(Item item) {
		if (item instanceof BeanItem) {
			return (BeanItem<?>) item;
		} else if (item instanceof WrappedItem) {
			return getBeanItem(((WrappedItem) item).getItem());
		}
		return null;
	}
}
