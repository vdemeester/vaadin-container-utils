package org.shortbrain.vaadin.container;

/**
 * An interface to define the method of {@link AliasBeanContainer} and related.
 * 
 * @author Vincent Demeester <vincent@shortbrain.org>
 * 
 * @since 0.2.0
 */
public interface AliasContainer {

    /**
     * Adds a shortcut container property for the container, e.g. "lieuVille" for "lieu.ville"
     * 
     * @param propertyId
     * @param propertyPath
     * @return true if the property was added
     */
    boolean addShortcutContainerProperty(String propertyId, String propertyPath);

    /**
     * Remove a container property for the container.
     * 
     * @param propertyId
     * @return true if the property was remove
     */
    boolean removeContainerProperty(String propertyId);
}
