package com.github.upcraftlp.digitalstorage.blockentity.container;

import com.github.upcraftlp.digitalstorage.blockentity.AccessTerminalBlockEntity;
import com.github.upcraftlp.digitalstorage.network.packet.TerminalContentS2CPacket;
import com.github.upcraftlp.digitalstorage.util.ItemStackWrapper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class AccessTerminalContainer extends DSContainer<AccessTerminalBlockEntity> {

    private final List<ItemStackWrapper> items = new LinkedList<>();

    public AccessTerminalContainer(int syncId, Identifier containerID, PlayerEntity player, PacketByteBuf byteBuf) {
        super(syncId, containerID, player, byteBuf);
        this.addInventoryRows(139);
    }

    public List<ItemStackWrapper> getItems() {
        return items;
    }

    @Override
    protected void readData(PacketByteBuf byteBuf) {
    }

    public void addItems(Collection<ItemStackWrapper> items, boolean sync, boolean reset) {
        if(reset) {
            this.items.clear();
        }
        items.forEach(this::add);
        if(sync) TerminalContentS2CPacket.send(this.player, this.syncId, false, items);
    }

    private void add(ItemStackWrapper wrapper) {
        if(!wrapper.isEmpty()) {
            boolean found = false;
            for(ItemStackWrapper w : this.items) {
                if(w.getStack().isItemEqual(wrapper.getStack())) {
                    w.addCount(wrapper.getCount());
                    found = true;
                    break;
                }
            }
            if(!found) {
                items.add(wrapper);
            }
        }
    }
}
