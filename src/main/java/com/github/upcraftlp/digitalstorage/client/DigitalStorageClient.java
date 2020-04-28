package com.github.upcraftlp.digitalstorage.client;

import com.github.glasspane.mesh.api.annotation.CalledByReflection;
import com.github.upcraftlp.digitalstorage.network.packet.DSPacketHandler;
import com.github.upcraftlp.digitalstorage.menu.DSMenus;
import net.fabricmc.api.ClientModInitializer;

@CalledByReflection
public class DigitalStorageClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        DSMenus.registerScreens();
        DSPacketHandler.initClient();

        //BlockEntityRendererRegistry.INSTANCE.register(DSBlockEntityTypes.ACCESS_TERMINAL, DigitalBlockEntityRenderer::new);
        //BlockEntityRendererRegistry.INSTANCE.register(DSBlockEntityTypes.DRIVE_BAY, DigitalBlockEntityRenderer::new);
    }
}
