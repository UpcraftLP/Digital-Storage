package com.github.upcraftlp.digitalstorage.client.screen;

import com.github.upcraftlp.digitalstorage.DigitalStorage;
import com.github.upcraftlp.digitalstorage.blockentity.container.DriveBayContainer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class DriveBayScreen extends ContainerScreen<DriveBayContainer> {

    private static final Identifier TEXTURE = new Identifier(DigitalStorage.MODID, "textures/gui/container/drive_bay.png");

    public DriveBayScreen(DriveBayContainer container, PlayerInventory playerInventory, Text name) {
        super(container, playerInventory, name);
    }

    @Override
    protected void drawBackground(float delta, int mouseX, int mouseY) {
        this.renderBackground();
        MinecraftClient.getInstance().getTextureManager().bindTexture(TEXTURE);
        int x = (this.width - this.containerWidth) / 2;
        int y = (this.height - this.containerHeight) / 2;
        this.blit(x, y, 0, 0, this.containerWidth, this.containerHeight);
    }
}
