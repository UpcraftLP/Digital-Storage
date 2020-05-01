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
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.ChunkPos;

import java.util.Collections;
import java.util.Set;

public class ClearDataS2CPacket {

    static final Identifier ID = DSNetworkHooks.getPacketID("clear", NetworkSide.CLIENTBOUND);

    public static void clearClientData(ServerPlayerEntity player) {
        clearClientData(player, Collections.emptySet());
    }

    @Environment(EnvType.CLIENT)
    static void onPacket(PacketContext ctx, PacketByteBuf byteBuf) {
        int chunkCount = byteBuf.readVarInt();
        ctx.getTaskQueue().execute(ClientNetworkInfo::clear);
        for(int i = 0; i < chunkCount; i++) {
            ChunkInfoS2CPacket.readChunkData(ctx, byteBuf);
        }
    }

    public static void clearClientData(ServerPlayerEntity player, Set<ChunkPos> chunksToSync) {
        PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());
        byteBuf.writeVarInt(chunksToSync.size());
        if(!chunksToSync.isEmpty()) {
            PersistentNetworkData data = PersistentNetworkData.get(player.getServerWorld());
            chunksToSync.forEach(pos -> ChunkInfoS2CPacket.writeChunkData(data.getConnectionsForChunk(pos), pos, byteBuf));
        }
        ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, ID, byteBuf);
    }
}
