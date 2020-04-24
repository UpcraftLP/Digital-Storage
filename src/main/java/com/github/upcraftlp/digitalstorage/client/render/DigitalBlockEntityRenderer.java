package com.github.upcraftlp.digitalstorage.client.render;

import com.github.upcraftlp.digitalstorage.blockentity.DigitalBlockEntity;
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
        if(blockEntity.hasLink()) {
            BlockPos start = blockEntity.getPos();
            BlockPos end = blockEntity.getLink();
            VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getLines());
            matrices.push();
            matrices.translate(-start.getX(), -start.getY(), -start.getZ());
            Matrix4f model = matrices.peek().getModel();
            buffer.vertex(model, start.getX() + 0.5F, start.getY() + 0.5F, start.getZ() + 0.5F).color(1.0F, 1.0F, 1.0F, 1.0F).next();
            buffer.vertex(model,end.getX() + 0.5F, end.getY() + 0.5F, end.getZ() + 0.5F).color(1.0F, 1.0F, 1.0F, 1.0F).next();
            matrices.pop();
        }
    }
}
