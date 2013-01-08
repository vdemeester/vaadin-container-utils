package org.shortbrain.vaadin.container.property;

import java.util.Collection;

/**
 * 
 * @author Vincent Demeester <vincent@shortbrain.org>
 * 
 * @param <IDTYPE>
 *            The type of the item identifier
 * @param <BEANTYPE>
 *            The type of the Bean
 * 
 * @since 0.2.0
 */
public interface HierarchicalBeanBuilder<IDTYPE, BEANTYPE> {

    /**
     * Get a {@link Collection} of IDTYPE for the given bean
     * 
     * @param bean
     *            The bean
     * @return The children of type IDTYPE
     */
    Collection<IDTYPE> getChildren(BEANTYPE bean);
}
