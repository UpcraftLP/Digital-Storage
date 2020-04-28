package com.github.upcraftlp.digitalstorage.network;

import com.github.upcraftlp.digitalstorage.api.PersistentNetworkData;
import com.github.upcraftlp.digitalstorage.network.packet.ChunkInfoS2CPacket;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.minecraft.container.Container;
import net.minecraft.network.NetworkSide;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.chunk.Chunk;

import java.util.UUID;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class DSNetworkHooks {

    /**
     * for when you want to do an update regardless of matching {@link Container#syncId},
     * or when the container's {@code syncId} is unknown
     */
    public static final int FORCE_SYNC = -1;
    private static final UUID DEBUG_NETWORK_ID = UUID.fromString("6c107f6a-0bc9-40e3-9774-6ca06d011c34");

    public static Identifier getPacketID(String name, NetworkSide targetSide) {
        return new Identifier("ds", String.format("%s_%s", targetSide == NetworkSide.CLIENTBOUND ? "s2c" : "c2s", name));
    }

    /**
     * @return whether or not the connection was successfully created
     */
    public static boolean connect(ServerWorld world, BlockPos pos1, Direction side1, BlockPos pos2, Direction side2) {
        //TODO use side parameters?
        //TODO handle network ID assignment
        PersistentNetworkData data = PersistentNetworkData.get(world);
        data.addConnection(DEBUG_NETWORK_ID, pos1, pos2);
        Chunk c1 = world.getChunk(pos1);
        Chunk c2 = world.getChunk(pos2);
        Stream.Builder<Chunk> builder = Stream.<Chunk>builder().add(c1);
        boolean different = c1 != c2;
        if(different) builder.accept(c2);
        builder.build().map(Chunk::getPos).flatMap(cpos -> PlayerStream.watching(world, cpos)).map(ServerPlayerEntity.class::cast).forEach(player -> {
            ChunkInfoS2CPacket.sendChunkData(player, world, c1, true);
            if(different) {
                ChunkInfoS2CPacket.sendChunkData(player, world, c2, true);
            }
        });
        return true;
    }

    public static int resetAll(MinecraftServer server) {
        return StreamSupport.stream(server.getWorlds().spliterator(), false).map(PersistentNetworkData::get).mapToInt(PersistentNetworkData::debugClearData).sum();
    }
}
