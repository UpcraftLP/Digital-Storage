package com.github.upcraftlp.digitalstorage.api.component;

import com.github.upcraftlp.digitalstorage.util.DSComponents;
import com.github.upcraftlp.digitalstorage.util.ItemStackWrapper;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.component.extension.SyncedComponent;
import nerdhub.cardinal.components.api.component.extension.TypeAwareComponent;
import net.minecraft.util.math.BlockPos;

import java.util.Collection;
import java.util.Set;

public interface DigitalNetworkPoint extends SyncedComponent, TypeAwareComponent {

    default boolean hasConnection() {
        return !getConnections().isEmpty();
    }

    Set<BlockPos> getConnections();

    //FIXME remove in favor of proper api
    Collection<ItemStackWrapper> getContentsTemp();

    @Override
    default ComponentType<?> getComponentType() {
        return DSComponents.NETWORK_COMPONENT;
    }
}
