package com.github.upcraftlp.digitalstorage.client.render;

import com.github.upcraftlp.digitalstorage.api.component.DigitalNetworkPoint;
import com.github.upcraftlp.digitalstorage.blockentity.DigitalBlockEntity;
import com.github.upcraftlp.digitalstorage.util.DSComponents;
import nerdhub.cardinal.components.api.component.BlockComponentProvider;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.Matrix4f;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;

public class DigitalBlockEntityRenderer<T extends DigitalBlockEntity<?>> extends BlockEntityRenderer<T> {

    public DigitalBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(T blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        DigitalNetworkPoint network = BlockComponentProvider.get(blockEntity.getCachedState()).getComponent(blockEntity.getWorld(), blockEntity.getPos(), DSComponents.NETWORK_COMPONENT, null);

        //right now, this means each connection will be rendered twice, but that's better than not rendering at all
        if(network != null && network.hasConnection()) {
            BlockPos start = blockEntity.getPos();
            VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getLines());
            matrices.push();
            matrices.translate(-start.getX(), -start.getY(), -start.getZ());
            Matrix4f model = matrices.peek().getModel();
            //buffer.vertex(model, 0.5F, 1.0F, 0.5F).color(0.0F, 0.0F, 0.0F, 1.0F).overlay(OverlayTexture.DEFAULT_UV).light(255).normal(matrices.peek().getNormal(), 0, 1, 0).next();
            //buffer.vertex(model, 0.5F, 2.0F, 0.5F).color(0.0F, 0.0F, 0.0F, 1.0F).overlay(OverlayTexture.DEFAULT_UV).light(255).normal(matrices.peek().getNormal(), 0, 1, 0).next();
            network.getConnections().forEach(end -> {
                buffer.vertex(model, start.getX() + 0.5F, start.getY() + 0.5F, start.getZ() + 0.5F).color(1.0F, 1.0F, 1.0F, 1.0F).next();
                buffer.vertex(model, end.getX() + 0.5F, end.getY() + 0.5F, end.getZ() + 0.5F).color(1.0F, 1.0F, 1.0F, 1.0F).next();
            });
            matrices.pop();
        }
    }
}
