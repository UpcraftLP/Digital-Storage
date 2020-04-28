package com.github.upcraftlp.digitalstorage.menu.container;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.container.Container;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

public abstract class DSContainer<T extends BlockEntity> extends Container {

    protected final PlayerEntity player;
    protected final BlockPos pos;
    protected final T blockEntity;
    private final Identifier containerID;

    @SuppressWarnings("unchecked")
    public DSContainer(int syncId, Identifier containerID, PlayerEntity player, PacketByteBuf byteBuf) {
        super(null, syncId);
        this.containerID = containerID;
        this.player = player;
        this.pos = byteBuf.readBlockPos();
        this.blockEntity = (T) player.world.getBlockEntity(this.pos);
        this.readData(byteBuf);
    }

    protected abstract void readData(PacketByteBuf byteBuf);

    protected void addInventoryRows(int yOffset) {
        addInventoryRows(yOffset, true);
    }

    protected void addInventoryRows(int yOffset, boolean includeHotbar) {
        int column, row;
        for(row = 0; row < 3; ++row) {
            for(column = 0; column < 9; column++) {
                this.addSlot(new Slot(player.inventory, column + row * 9 + 9, 8 + column * 18, yOffset + row * 18));
            }
        }
        if(includeHotbar) {
            for(column = 0; column < 9; column++) {
                this.addSlot(new Slot(player.inventory, column, 8 + column * 18, yOffset + 58));
            }
        }
    }

    public Identifier getContainerID() {
        return containerID;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return pos.getSquaredDistance(player.getX(), player.getY(), player.getZ(), true) <= 64.0D;
    }
}
