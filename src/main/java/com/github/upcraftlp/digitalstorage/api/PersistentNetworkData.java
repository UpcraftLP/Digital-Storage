package com.github.upcraftlp.digitalstorage.api;

import com.github.upcraftlp.digitalstorage.DigitalStorage;
import com.github.upcraftlp.digitalstorage.api.component.LinkHolder;
import com.github.upcraftlp.digitalstorage.network.packet.ClearDataS2CPacket;
import com.github.upcraftlp.digitalstorage.util.DSComponents;
import nerdhub.cardinal.components.api.component.BlockComponentProvider;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.PersistentState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

public class PersistentNetworkData extends PersistentState {

    private static final String ID = DigitalStorage.MODID + "_network_data";
    private final ServerWorld world;
    private final Map<UUID, Network> networks = new HashMap<>();
    private final Map<ChunkPos, Map<UUID, List<Connection>>> chunkConnectionCache = new HashMap<>();

    private PersistentNetworkData(ServerWorld world) {
        super(ID);
        this.world = world;
    }

    public static PersistentNetworkData get(ServerWorld world) {
        return world.getPersistentStateManager().getOrCreate(() -> new PersistentNetworkData(world), ID);
    }

    public void addConnection(UUID network, BlockPos pos1, BlockPos pos2) {
        networks.computeIfAbsent(network, key -> new Network()).addConnection(pos1, pos2);
        Stream.of(new Pair<>(pos1, pos2), new Pair<>(pos2, pos1)).forEach(pair -> {
            BlockState state = world.getBlockState(pair.getLeft());
            BlockComponentProvider.get(state).optionally(world, pair.getLeft(), DSComponents.NETWORK_COMPONENT, null).ifPresent(digitalNetworkPoint -> {
                //TODO get rid of this special casing
                if(digitalNetworkPoint instanceof LinkHolder) {
                    ((LinkHolder) digitalNetworkPoint).setConnection(pair.getRight());
                }
            });
        });
        this.markDirty();
    }

    public Set<UUID> getNetworksForChunk(ChunkPos chunk) {
        return getConnectionsForChunk(chunk).keySet();
    }

    public Map<UUID, List<Connection>> getConnectionsForChunk(ChunkPos chunk) {
        return chunkConnectionCache.computeIfAbsent(chunk, cPos -> {
            Map<UUID, List<Connection>> ret = new HashMap<>();
            networks.keySet().forEach(uuid -> {
                Network network = networks.get(uuid);
                network.getConnections().forEach((pos1, pos2) -> {
                    if(isInChunk(chunk, pos1) || isInChunk(chunk, pos2)) {
                        ret.computeIfAbsent(uuid, id -> new ArrayList<>()).add(new Connection(pos1, pos2));
                    }
                });
            });
            return ret;
        });
    }

    private static boolean isInChunk(ChunkPos chunk, BlockPos toTest) {
        return toTest.getX() >> 4 == chunk.x && toTest.getZ() >> 4 == chunk.z;
    }

    @Override
    public void fromTag(CompoundTag tag) {
        networks.clear();
        chunkConnectionCache.clear();
        ListTag networkList = tag.getList("networks", NbtType.COMPOUND);
        for(int i = 0; i < networkList.size(); i++) {
            CompoundTag networkInfo = networkList.getCompound(i);
            UUID id = NbtHelper.toUuid(networkInfo.getCompound("id"));
            ListTag connectionList = networkInfo.getList("connections", NbtType.COMPOUND);
            Network network = new Network();
            for(int j = 0; j < connectionList.size(); j++) {
                CompoundTag connectionInfo = connectionList.getCompound(j);
                BlockPos pos1 = NbtHelper.toBlockPos(connectionInfo.getCompound("source"));
                BlockPos pos2 = NbtHelper.toBlockPos(connectionInfo.getCompound("target"));
                network.addConnection(pos1, pos2);
            }
            networks.put(id, network);
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        ListTag networkList = new ListTag();
        networks.forEach((uuid, network) -> {
            CompoundTag networkInfo = new CompoundTag();
            networkInfo.put("id", NbtHelper.fromUuid(uuid));
            ListTag connectionList = new ListTag();
            network.connections.forEach((pos1, pos2) -> {
                CompoundTag connectionInfo = new CompoundTag();
                connectionInfo.put("source", NbtHelper.fromBlockPos(pos1));
                connectionInfo.put("target", NbtHelper.fromBlockPos(pos2));
                connectionList.add(connectionInfo);
            });
            networkInfo.put("connections", connectionList);
            networkList.add(networkInfo);
        });
        tag.put("networks", networkList);
        return tag;
    }

    @Override
    public void markDirty() {
        super.markDirty();
        this.chunkConnectionCache.clear();
    }

    public int debugClearData() {
        int count = this.networks.size();
        DigitalStorage.getLogger().warn("clearing {} networks in dimension {}", this.networks.size(), Registry.DIMENSION_TYPE.getId(this.world.getDimension().getType()));
        this.networks.clear();
        this.markDirty();
        this.world.getPlayers().forEach(ClearDataS2CPacket::clearClientData);
        return count;
    }

    //TODO extract elsewhere
    public static class Connection {
        private final BlockPos pos1;
        private final BlockPos pos2;

        public Connection(BlockPos pos1, BlockPos pos2) {
            this.pos1 = pos1;
            this.pos2 = pos2;
        }

        public BlockPos getPos1() {
            return pos1;
        }

        public BlockPos getPos2() {
            return pos2;
        }
    }

    //TODO extract elsewhere; combine with graph
    private static class Network {

        private final Map<BlockPos, BlockPos> connections = new HashMap<>();

        public Map<BlockPos, BlockPos> getConnections() {
            return connections;
        }

        public void addConnection(BlockPos pos1, BlockPos pos2) {
            connections.put(pos1, pos2);
        }
    }
}
