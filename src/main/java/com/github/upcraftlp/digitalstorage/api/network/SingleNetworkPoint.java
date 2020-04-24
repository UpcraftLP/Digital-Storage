package com.github.upcraftlp.digitalstorage.api.network;

import java.util.Collection;
import java.util.Collections;

public interface SingleNetworkPoint extends NetworkPoint {
    @Override
    default Collection<NetworkPoint> getConnections() {
        return Collections.singleton(this.getConnection());
    }

    NetworkPoint getConnection();
}
