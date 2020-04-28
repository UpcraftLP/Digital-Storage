package com.github.upcraftlp.digitalstorage.network.packet;

import com.github.upcraftlp.digitalstorage.api.PersistentNetworkData;
import com.github.upcraftlp.digitalstorage.client.ClientNetworkInfo;
import com.github.upcraftlp.digitalstorage.network.DSNetworkHooks;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.PacketContext;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.network.NetworkSide;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ChunkInfoS2CPacket {

    static final Identifier ID = DSNetworkHooks.getPacketID("chunk_info", NetworkSide.CLIENTBOUND);

    public static void sendChunkData(ServerPlayerEntity target, ServerWorld world, Chunk chunk) {
        sendChunkData(target, world, chunk, false);
    }

    public static void sendChunkData(ServerPlayerEntity target, ServerWorld world, Chunk chunk, boolean forceSync) {
        Map<UUID, List<PersistentNetworkData.Connection>> networkConnections = PersistentNetworkData.get(world).getConnectionsForChunk(chunk.getPos());
        if(forceSync || !networkConnections.isEmpty()) {
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            buf.writeLong(chunk.getPos().toLong());
            buf.writeVarInt(networkConnections.size());
            networkConnections.forEach((uuid, connectionList) -> {
                buf.writeUuid(uuid);
                buf.writeVarInt(connectionList.size());
                connectionList.forEach(connection -> {
                    buf.writeBlockPos(connection.getPos1());
                    buf.writeBlockPos(connection.getPos2());
                });
            });
            ServerSidePacketRegistry.INSTANCE.sendToPlayer(target, ID, buf);
        }
    }

    @Environment(EnvType.CLIENT)
    static void onPacket(PacketContext ctx, PacketByteBuf byteBuf) {
        ChunkPos pos = new ChunkPos(byteBuf.readLong());
        int mapSize = byteBuf.readVarInt();
        Map<UUID, List<PersistentNetworkData.Connection>> connections = new HashMap<>(mapSize, 1.0F);
        for(int i = 0; i < mapSize; i++) {
            UUID networkID = byteBuf.readUuid();
            int connectionSize = byteBuf.readVarInt();
            List<PersistentNetworkData.Connection> connectionList = new ArrayList<>(connectionSize);
            for(int j = 0; j < connectionSize; j++) {
                BlockPos source = byteBuf.readBlockPos();
                BlockPos target = byteBuf.readBlockPos();
                PersistentNetworkData.Connection connection = new PersistentNetworkData.Connection(source, target);
                connectionList.add(connection);
            }
            connections.put(networkID, connectionList);
        }
        ctx.getTaskQueue().execute(() -> ClientNetworkInfo.updateConnections(pos, connections));
    }
}
