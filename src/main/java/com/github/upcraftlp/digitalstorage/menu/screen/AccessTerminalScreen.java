package com.github.upcraftlp.digitalstorage.menu.screen;

import com.github.upcraftlp.digitalstorage.DigitalStorage;
import com.github.upcraftlp.digitalstorage.menu.container.AccessTerminalContainer;
import com.github.upcraftlp.digitalstorage.menu.DsFormatter;
import com.github.upcraftlp.digitalstorage.util.ItemStackWrapper;
import com.mojang.blaze3d.systems.RenderSystem;
import me.shedaniel.clothconfig2.api.ScissorsHandler;
import me.shedaniel.math.api.Rectangle;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class AccessTerminalScreen extends ContainerScreen<AccessTerminalContainer> {

    private static final Identifier TEXTURE = new Identifier(DigitalStorage.NAMESPACE, "textures/gui/container/access_terminal.png");

    public AccessTerminalScreen(AccessTerminalContainer container, PlayerInventory playerInventory, Text name) {
        super(container, playerInventory, name);
        this.containerHeight = 222;
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        super.render(mouseX, mouseY, delta);
        int x = (this.width - this.containerWidth) / 2;
        int y = (this.height - this.containerHeight) / 2;
        int itemsX = x + 8;
        int itemsY = y + 18;
        //TODO remove REI hard dep
        Rectangle itemListBounds = new Rectangle(itemsX, itemsY, 160, 106);
        ScissorsHandler.INSTANCE.scissor(itemListBounds);
        {
            int count = 0;
            long time = System.nanoTime() / 1_000_000L;
            int move = 0; //(int) (time % 5000 / 200);
            List<ItemStackWrapper> items = this.container.getItems();
            int row, column, posX, posY;
            for(int i = 0; i < 54 && i < items.size(); i++) {
                row = i / 9;
                column = i % 9;
                ItemStackWrapper stack = items.get(i);
                posX = itemsX + column * 18;
                posY = move + itemsY + row * 18;
                this.drawStackWithCount(stack, posX, posY);
                if(this.isPointWithinBounds(posX, posY, 16, 16, mouseX + this.x, mouseY + this.y)) {
                    fill(posX, posY, posX + 16, posY + 16, 0x80FFFFFF);
                }
            }
        }
        ScissorsHandler.INSTANCE.removeLastScissor();
        this.drawMouseoverTooltip(mouseX, mouseY);
    }

    @Override
    protected void drawForeground(int mouseX, int mouseY) {
        this.font.draw(this.title.asFormattedString(), 8.0F, 6.0F, 4210752);
        this.font.draw(this.playerInventory.getDisplayName().asFormattedString(), 8.0F, (float) (this.containerHeight - 96 + 2), 4210752);
    }

    @Override
    protected void drawBackground(float delta, int mouseX, int mouseY) {
        this.renderBackground();
        MinecraftClient.getInstance().getTextureManager().bindTexture(TEXTURE);
        int x = (this.width - this.containerWidth) / 2;
        int y = (this.height - this.containerHeight) / 2;
        this.blit(x, y, 0, 0, this.containerWidth, this.containerHeight);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button);
        //TODO find the slot that was clicked at
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return super.mouseReleased(mouseX, mouseY, button);
    }

    private void drawStackWithCount(ItemStackWrapper stack, int xPosition, int yPosition) {
        RenderSystem.pushMatrix();
        RenderSystem.translatef(0.0F, 0.0F, 32.0F);
        this.setBlitOffset(200);
        this.itemRenderer.zOffset = 200.0F;
        this.itemRenderer.renderGuiItem(stack.getStack(), xPosition, yPosition);
        this.itemRenderer.renderGuiItemOverlay(this.font, stack.getStack(), xPosition, yPosition, null);
        RenderSystem.translated(0.0D, 0.0D, this.itemRenderer.zOffset + 200.0D);
        RenderSystem.scaled(0.5D, 0.5D, 1.0D);
        String countStr = DsFormatter.format(stack.getCount());
        this.font.drawWithShadow(countStr, (xPosition + 16 - this.font.getStringWidth(countStr) / 2.0F) * 2.0F, (yPosition + 11.5F) * 2.0F, 16777215);
        this.setBlitOffset(0);
        this.itemRenderer.zOffset = 0.0F;
        RenderSystem.popMatrix();
    }
}
