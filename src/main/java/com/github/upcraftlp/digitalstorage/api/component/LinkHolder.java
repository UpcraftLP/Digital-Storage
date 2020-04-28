package com.github.upcraftlp.digitalstorage.api.component;

import com.github.upcraftlp.digitalstorage.blockentity.DigitalBlockEntity;
import com.github.upcraftlp.digitalstorage.util.ItemStackWrapper;
import nerdhub.cardinal.components.api.util.sync.BaseSyncedComponent;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.fabricmc.fabric.api.network.PacketContext;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
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
    public Collection<ItemStackWrapper> getContentsTemp() {
        return this.anchor instanceof DigitalBlockEntity ? ((DigitalBlockEntity<?>) this.anchor).getContentsTemp() : Collections.emptySet();
    }

    @Override
    public BlockPos getPosition() {
        return this.anchor.getPos();
    }

    @Override
    public World getWorld() {
        return this.anchor.getWorld();
    }

    @Override
    public BlockPos getConnection() {
        return Objects.requireNonNull(connection, "getConnection() called without hasConnection()!");
    }

    public void setConnection(@Nullable BlockPos connection) {
        if(this.hasConnection()) { //clean up the old connection first
            BlockState state = this.getWorld().getBlockState(this.getConnection());
            //FIXME fix
            //BlockComponentProvider.get(state).optionally(this.getWorld(), this.getConnection(), DSComponents.NETWORK_COMPONENT, null).ifPresent(target -> target.removeConnection(this.getPosition()));
        }
        this.connection = connection;
        this.sync();
    }

    @Override
    public void sync() {
        this.anchor.markDirty();
        if(this.anchor instanceof BlockEntityClientSerializable) {
            ((BlockEntityClientSerializable) this.anchor).sync();
        }
        else {
            PlayerStream.watching(this.anchor).map(ServerPlayerEntity.class::cast).forEach(this::syncWith);
        }
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
        this.connection = compoundTag.getBoolean("Linked") ? NbtHelper.toBlockPos(compoundTag.getCompound("Pos")) : null;
    }

    @Override
    public CompoundTag toTag(CompoundTag compoundTag) {
        compoundTag.putBoolean("Linked", this.hasConnection());
        if(this.hasConnection()) {
            compoundTag.put("Pos", NbtHelper.fromBlockPos(this.getConnection()));
        }
        else {
            compoundTag.remove("Pos");
        }
        return compoundTag;
    }
}
