package com.github.upcraftlp.digitalstorage.util;

import com.github.upcraftlp.digitalstorage.DigitalStorage;
import com.github.upcraftlp.digitalstorage.blockentity.container.AccessTerminalContainer;
import com.github.upcraftlp.digitalstorage.blockentity.container.DriveBayContainer;
import com.github.upcraftlp.digitalstorage.client.screen.AccessTerminalScreen;
import com.github.upcraftlp.digitalstorage.client.screen.DriveBayScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.NotNull;

public class DSMenus {

    public static final Identifier ACCESS_TERMINAL = new Identifier(DigitalStorage.MODID, "access_terminal");
    public static final Identifier DRIVE_BAY = new Identifier(DigitalStorage.MODID, "drive_bay");

    public static void registerContainers() {
        ContainerProviderRegistry.INSTANCE.registerFactory(ACCESS_TERMINAL, AccessTerminalContainer::new);
        ContainerProviderRegistry.INSTANCE.registerFactory(DRIVE_BAY, DriveBayContainer::new);
    }

    @Environment(EnvType.CLIENT)
    public static void registerScreens() {
        ScreenProviderRegistry.INSTANCE.<AccessTerminalContainer>registerFactory(ACCESS_TERMINAL, container -> new AccessTerminalScreen(container, getPlayerInventory(), createScreenTitle(ACCESS_TERMINAL)));
        ScreenProviderRegistry.INSTANCE.<DriveBayContainer>registerFactory(DRIVE_BAY, container -> new DriveBayScreen(container, getPlayerInventory(), createScreenTitle(DRIVE_BAY)));
    }

    private static Text createScreenTitle(Identifier name) {
        return new TranslatableText(Util.createTranslationKey("screen", name));
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    @Environment(EnvType.CLIENT)
    private static PlayerInventory getPlayerInventory() {
        return MinecraftClient.getInstance().player.inventory;
    }
}
