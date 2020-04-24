package com.github.upcraftlp.digitalstorage.blockentity;

import com.github.upcraftlp.digitalstorage.blockentity.container.DriveBayContainer;
import com.github.upcraftlp.digitalstorage.init.DSBlockEntityTypes;
import com.github.upcraftlp.digitalstorage.util.DSMenus;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;

public class DriveBayBlockEntity extends DigitalBlockEntity<DriveBayContainer> implements Inventory {

    public static final int INVENTORY_SIZE = 6;
    private final BasicInventory inventory = new BasicInventory(INVENTORY_SIZE) {
        @Override
        public void markDirty() {
            super.markDirty();
            DriveBayBlockEntity.this.markDirty();
        }
    };

    public DriveBayBlockEntity() {
        super(DSBlockEntityTypes.DRIVE_BAY);
    }

    @Override
    public Class<DriveBayContainer> getContainerClass() {
        return DriveBayContainer.class;
    }

    @Override
    public Identifier getContainerID() {
        return DSMenus.DRIVE_BAY;
    }

    @Override
    public void writeData(PacketByteBuf byteBuf) {
    }

    @Override
    public int getInvSize() {
        return this.inventory.getInvSize();
    }

    @Override
    public boolean isInvEmpty() {
        return this.inventory.isInvEmpty();
    }

    @Override
    public ItemStack getInvStack(int slot) {
        return this.inventory.getInvStack(slot);
    }

    @Override
    public ItemStack takeInvStack(int slot, int amount) {
        return this.inventory.takeInvStack(slot, amount);
    }

    @Override
    public ItemStack removeInvStack(int slot) {
        return this.inventory.removeInvStack(slot);
    }

    @Override
    public void setInvStack(int slot, ItemStack stack) {
        this.inventory.setInvStack(slot, stack);
    }

    @Override
    public boolean canPlayerUseInv(PlayerEntity player) {
        return this.inventory.canPlayerUseInv(player);
    }

    @Override
    public void clear() {
        this.inventory.clear();
    }
}
