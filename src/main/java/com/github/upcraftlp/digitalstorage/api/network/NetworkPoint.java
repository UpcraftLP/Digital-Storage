package com.github.upcraftlp.digitalstorage.api.network;

import com.github.upcraftlp.digitalstorage.util.ItemStackWrapper;
import nerdhub.cardinal.components.api.component.Component;
import net.minecraft.nbt.CompoundTag;

import java.util.Collection;
import java.util.UUID;

/**
 * component implementation for whether something can interact with the network
 */
public interface NetworkPoint extends Component {

    @Override
    default void fromTag(CompoundTag tag) {
        //NO-OP
    }

    @Override
    default CompoundTag toTag(CompoundTag tag) {
        //NO-OP
        return tag;
    }

    //FIXME remove in favor of proper api
    Collection<ItemStackWrapper> getContentsTemp();

    UUID getDeviceID();

    UUID getNetworkID();

    Collection<NetworkPoint> getConnections();

}
