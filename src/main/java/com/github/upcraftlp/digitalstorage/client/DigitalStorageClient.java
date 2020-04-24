package com.github.upcraftlp.digitalstorage.client;

import com.github.glasspane.mesh.api.annotation.CalledByReflection;
import com.github.upcraftlp.digitalstorage.client.render.DigitalBlockEntityRenderer;
import com.github.upcraftlp.digitalstorage.init.DSBlockEntityTypes;
import com.github.upcraftlp.digitalstorage.network.packet.NetworkPointSyncS2CPacket;
import com.github.upcraftlp.digitalstorage.network.packet.TerminalContentS2CPacket;
import com.github.upcraftlp.digitalstorage.util.DSMenus;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;

@CalledByReflection
public class DigitalStorageClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        DSMenus.registerScreens();
        ClientSidePacketRegistry.INSTANCE.register(TerminalContentS2CPacket.ID, TerminalContentS2CPacket::onPacket);
        ClientSidePacketRegistry.INSTANCE.register(NetworkPointSyncS2CPacket.ID, NetworkPointSyncS2CPacket::onPacket);

        BlockEntityRendererRegistry.INSTANCE.register(DSBlockEntityTypes.ACCESS_TERMINAL, DigitalBlockEntityRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(DSBlockEntityTypes.DRIVE_BAY, DigitalBlockEntityRenderer::new);
    }
}
