package com.github.upcraftlp.digitalstorage.api.storage;

/**
 * each storage action has a type.
 */
public enum StorageActionType {
    /**
     * read the current state of the device.
     * this may or may not include:
     * <ul>
     *     <li>items, fluids or other content stored in the device</li>
     *     <li>any active internal actions</li>
     * </ul>
     */
    READ,
    /**
     * interact with the device in a way that changes it AND the network.
     * examples:
     * <ul>
     *     <li>adding items to an external inventory</li>
     *     <li>removing items from an external inventory</li>
     * </ul>
     */
    MODIFY,
    /**
     * interact with the device in a way that changes its internal state, but does <strong>NOT</strong> affect the network directly
     * examples:
     * <ul>
     *     <li>making a crafter craft one item</li>
     *     <li>making a furnace smelt one item</li>
     * </ul>
     */
    MUTATE
}
