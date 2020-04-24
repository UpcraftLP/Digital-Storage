package com.github.upcraftlp.digitalstorage.api.component;

import net.minecraft.util.math.BlockPos;

import java.util.Collections;
import java.util.Set;

public interface DigitalNetworkPointSingle extends DigitalNetworkPoint {

    @Override
    default Set<BlockPos> getConnections() {
        return hasConnection() ? Collections.singleton(getConnection()) : Collections.emptySet();
    }

    @Override
    boolean hasConnection();

    BlockPos getConnection();
}
