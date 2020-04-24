package com.github.upcraftlp.digitalstorage.network.packet;

import com.github.upcraftlp.digitalstorage.blockentity.container.AccessTerminalContainer;
import com.github.upcraftlp.digitalstorage.util.ItemStackWrapper;
import com.github.upcraftlp.digitalstorage.util.network.NetworkHooks;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.PacketContext;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.NetworkSide;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TerminalContentS2CPacket {

    public static final Identifier ID = NetworkHooks.getPacketID("terminal_content", NetworkSide.CLIENTBOUND);

    public static void send(PlayerEntity player, int syncId, boolean reset, Collection<ItemStackWrapper> items) {
        PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());
        byteBuf.writeVarInt(syncId);
        byteBuf.writeBoolean(reset);
        byteBuf.writeVarInt(items.size());
        items.forEach(itemStackWrapper -> itemStackWrapper.toPacket(byteBuf));
        ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, ID, byteBuf);
    }

    @Environment(EnvType.CLIENT)
    @SuppressWarnings("ConstantConditions")
    public static void onPacket(PacketContext ctx, PacketByteBuf byteBuf) {
        int syncID = byteBuf.readVarInt();
        boolean reset = byteBuf.readBoolean();
        int size = byteBuf.readVarInt();
        List<ItemStackWrapper> items = new ArrayList<>(size);
        for(int i = 0; i < size; i++) {
            ItemStackWrapper wrapper = ItemStackWrapper.fromPacket(byteBuf);
            items.add(wrapper);
        }
        ctx.getTaskQueue().execute(() -> {
            Container clientContainer = MinecraftClient.getInstance().player.container;
            if(syncID == clientContainer.syncId || syncID == NetworkHooks.FORCE_SYNC) {
                ((AccessTerminalContainer) clientContainer).addItems(items, false, reset);
            }
        });
    }
}
