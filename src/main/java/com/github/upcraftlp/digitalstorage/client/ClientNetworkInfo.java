package com.github.upcraftlp.digitalstorage.client;

import com.github.upcraftlp.digitalstorage.api.PersistentNetworkData;
import net.minecraft.util.math.ChunkPos;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ClientNetworkInfo {

    private static final Map<ChunkPos, Map<UUID, List<PersistentNetworkData.Connection>>> CHUNK_CONNECTION_MAP = new HashMap<>();

    public static void updateConnections(ChunkPos pos, Map<UUID, List<PersistentNetworkData.Connection>> connections) {
        CHUNK_CONNECTION_MAP.put(pos, connections);
    }

    public static Map<UUID, List<PersistentNetworkData.Connection>> getCableConnections(ChunkPos pos) {
        return CHUNK_CONNECTION_MAP.getOrDefault(pos, Collections.emptyMap());
    }

    public static void clear() {
        CHUNK_CONNECTION_MAP.clear();
    }
}
