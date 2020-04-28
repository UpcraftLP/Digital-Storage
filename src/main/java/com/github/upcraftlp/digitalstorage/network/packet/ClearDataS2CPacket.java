package com.github.upcraftlp.digitalstorage.network.packet;

import com.github.upcraftlp.digitalstorage.client.ClientNetworkInfo;
import com.github.upcraftlp.digitalstorage.network.DSNetworkHooks;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.PacketContext;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.NetworkSide;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;

public class ClearDataS2CPacket {

    static final Identifier ID = DSNetworkHooks.getPacketID("clear", NetworkSide.CLIENTBOUND);

    public static void clearClientData(PlayerEntity player) {
        PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());
        ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, ID, byteBuf);
    }

    @Environment(EnvType.CLIENT)
    static void onPacket(PacketContext ctx, PacketByteBuf unused) {
        ctx.getTaskQueue().execute(ClientNetworkInfo::clear);
    }
}
