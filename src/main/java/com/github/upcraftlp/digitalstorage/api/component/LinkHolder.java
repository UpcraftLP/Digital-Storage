package com.github.upcraftlp.digitalstorage.api.component;

import nerdhub.cardinal.components.api.util.sync.BaseSyncedComponent;
import net.fabricmc.fabric.api.network.PacketContext;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class LinkHolder implements DigitalNetworkPointSingle, BaseSyncedComponent {

    private final BlockEntity anchor;
    @Nullable
    private BlockPos connection;

    public LinkHolder(BlockEntity anchor) {
        this.anchor = anchor;
    }

    @Override
    public boolean hasConnection() {
        return connection != null;
    }

    @Override
    public BlockPos getConnection() {
        return Objects.requireNonNull(connection, "getConnection() called without hasConnection()!");
    }

    @Override
    public void sync() {
        this.anchor.markDirty();
        PlayerStream.watching(this.anchor).map(ServerPlayerEntity.class::cast).forEach(this::syncWith);
    }

    @Override
    public void syncWith(ServerPlayerEntity serverPlayerEntity) {
        @Nullable BlockEntityUpdateS2CPacket packet = this.anchor.toUpdatePacket();
        if(packet != null) {
            serverPlayerEntity.networkHandler.sendPacket(packet);
        }
    }

    @Override
    public void processPacket(PacketContext packetContext, PacketByteBuf packetByteBuf) {
        //NO-OP
    }

    @Override
    public void fromTag(CompoundTag compoundTag) {

    }

    @Override
    public CompoundTag toTag(CompoundTag compoundTag) {

        return compoundTag;
    }
}
