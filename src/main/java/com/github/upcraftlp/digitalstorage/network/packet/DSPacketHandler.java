package com.github.upcraftlp.digitalstorage.network.packet;

import com.github.upcraftlp.digitalstorage.DigitalStorage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;

public class DSPacketHandler {

    public static void init() {
        DigitalStorage.getLogger().trace("registering packets");
    }

    @Environment(EnvType.CLIENT)
    public static void initClient() {
        DigitalStorage.getLogger().trace("registering client packets");
        ClientSidePacketRegistry.INSTANCE.register(TerminalContentS2CPacket.ID, TerminalContentS2CPacket::onPacket);
        ClientSidePacketRegistry.INSTANCE.register(NetworkPointSyncS2CPacket.ID, NetworkPointSyncS2CPacket::onPacket);
        ClientSidePacketRegistry.INSTANCE.register(ChunkInfoS2CPacket.ID, ChunkInfoS2CPacket::onPacket);
        ClientSidePacketRegistry.INSTANCE.register(ClearDataS2CPacket.ID, ClearDataS2CPacket::onPacket);
    }
}
