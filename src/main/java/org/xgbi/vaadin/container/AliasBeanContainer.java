package org.xgbi.vaadin.container;

import java.lang.reflect.Field;
import java.util.Map;

import com.vaadin.data.util.AbstractBeanContainer;
import com.vaadin.data.util.AliasPropertyDescriptor;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.VaadinPropertyDescriptor;

/**
 * An extension of the {@link BeanContainer}, which adds shorcuts properties.
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
 */
public class AliasBeanContainer<IDTYPE, BEANTYPE> extends com.vaadin.data.util.BeanContainer<IDTYPE, BEANTYPE>
        implements AliasContainer {

    private static final long serialVersionUID = 2865701930991415312L;
    private Field modelField;

    /**
     * Create a {@link AliasBeanContainer}
     * 
     * @param type
     *            The type of the Bean
     */
    public AliasBeanContainer(Class<? super BEANTYPE> type) {
        super(type);
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

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public boolean addShortcutContainerProperty(String propertyId, String propertyPath) {
        return addContainerProperty(propertyId, new AliasPropertyDescriptor(propertyId, propertyPath, getBeanType()));
    }
    
    public boolean removeContainerProperty(String propertyId) {
        // FIXME handle things better.
        if (modelField != null) {
            try {
                Map<String, VaadinPropertyDescriptor<BEANTYPE>> model = (Map<String, VaadinPropertyDescriptor<BEANTYPE>>) modelField.get(this);
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
