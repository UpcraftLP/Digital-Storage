package com.github.upcraftlp.digitalstorage.network.packet;

import com.github.upcraftlp.digitalstorage.DigitalStorage;
import com.github.upcraftlp.digitalstorage.network.DSNetworkHooks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.NetworkSide;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;

public class NetworkPointSyncS2CPacket {

    static final Identifier ID = DSNetworkHooks.getPacketID("network_ap", NetworkSide.CLIENTBOUND);

    public static void send(PlayerEntity target, PacketByteBuf data) {
        DigitalStorage.getLogger().error("not implemented!", new RuntimeException("stacktrace"));
        //ServerSidePacketRegistry.INSTANCE.sendToPlayer(target, ID, data);
    }

    @Environment(EnvType.CLIENT)
    static void onPacket(PacketContext ctx, PacketByteBuf byteBuf) {

    }
}
