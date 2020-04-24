package com.github.upcraftlp.digitalstorage.blockentity.container;

import com.github.upcraftlp.digitalstorage.blockentity.DriveBayBlockEntity;
import com.github.upcraftlp.digitalstorage.util.DSTags;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;

public class DriveBayContainer extends DSContainer<DriveBayBlockEntity> {

    private final Inventory inputInv;
    private final Inventory outputInv;
    private final Inventory driveInv;
    private Runnable changeListener = () -> {};

    public DriveBayContainer(int syncId, Identifier containerID, PlayerEntity player, PacketByteBuf byteBuf) {
        super(syncId, containerID, player, byteBuf);
        this.inputInv = new BasicInventory(1) {
            @Override
            public void markDirty() {
                super.markDirty();
                DriveBayContainer.this.onContentChanged(this);
                DriveBayContainer.this.changeListener.run();
            }

            @Override
            public int getInvMaxStackAmount() {
                return 1;
            }
        };
        this.outputInv = new BasicInventory(1) {
            @Override
            public void markDirty() {
                super.markDirty();
                DriveBayContainer.this.changeListener.run();
            }

            @Override
            public int getInvMaxStackAmount() {
                return 1;
            }
        };
        this.driveInv = this.blockEntity;
        this.addSlot(new Slot(inputInv, 0, 8, 14));
        this.addSlot(new Slot(outputInv, 0, 8, 55));
        this.addInventoryRows(84);

        this.inputInv.setInvStack(0, new ItemStack(Items.DIAMOND));
    }

    @Override
    protected void readData(PacketByteBuf byteBuf) {
    }

    public static class DriveSlot extends Slot {

        public DriveSlot(Inventory inventory, int invSlot, int xPosition, int yPosition) {
            super(inventory, invSlot, xPosition, yPosition);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return stack.getItem().isIn(DSTags.ITEM_HARD_DRIVES);
        }
    }
}
