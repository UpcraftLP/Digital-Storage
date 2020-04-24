package com.github.upcraftlp.digitalstorage.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.PacketByteBuf;

public final class ItemStackWrapper {

    private final ItemStack stack;
    private long count;

    public static ItemStackWrapper fromPacket(PacketByteBuf byteBuf) {
        long count = byteBuf.readVarLong();
        ItemStack stack = count > 0 ? byteBuf.readItemStack() : ItemStack.EMPTY;
        return new ItemStackWrapper(stack, count);
    }

    public ItemStackWrapper(ItemStack stack) {
        this(stack, stack.getCount());
    }

    public ItemStackWrapper(ItemStack stack, long count) {
        this.stack = stack;
        this.count = count;
        this.stack.setCount(1);
    }

    public ItemStack getStack() {
        return stack;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public boolean isEmpty() {
        return this.getStack().getCount() == 0;
    }

    public void toPacket(PacketByteBuf byteBuf) {
        byteBuf.writeVarLong(this.count);
        if(this.count > 0) {
            byteBuf.writeItemStack(this.stack);
        }
    }

    public void addCount(long count) {
        this.count += count;
    }
}
