package com.github.upcraftlp.digitalstorage.client.render;

import com.github.upcraftlp.digitalstorage.client.ClientNetworkInfo;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.Matrix4f;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.Profiler;

@Environment(EnvType.CLIENT)
public class NetworkConnectionRenderer {

    private static final MinecraftClient CLIENT = MinecraftClient.getInstance();

    public static void render(MatrixStack matrixStack, Profiler profiler, float tickDelta, VertexConsumerProvider.Immediate immediate, Vec3d cameraPos, ObjectList<WorldRenderer.ChunkInfo> visibleChunks) {
        if(CLIENT.world != null) {
            matrixStack.push();
            {
                matrixStack.translate(-cameraPos.getX(), -cameraPos.getY(), -cameraPos.getZ());
                Matrix4f modelMatrix = matrixStack.peek().getModel();
                VertexConsumer buffer = immediate.getBuffer(RenderLayer.getLines());
                for(WorldRenderer.ChunkInfo vChunk : visibleChunks) {
                    ChunkPos chunkPos = new ChunkPos(vChunk.chunk.getOrigin());
                    ClientNetworkInfo.getCableConnections(chunkPos).forEach((uuid, connections) -> {
                        profiler.push("network_" + uuid.toString());
                        //FIXME right now we are potentially rendering connections twice
                        connections.forEach(connection -> {
                            Vector3f start = new Vector3f(connection.getPos1().getX() + 0.5F, connection.getPos1().getY() + 0.5F, connection.getPos1().getZ() + 0.5F);
                            Vector3f end = new Vector3f(connection.getPos2().getX() + 0.5F, connection.getPos2().getY() + 0.5F, connection.getPos2().getZ() + 0.5F);
                            buffer.vertex(modelMatrix, start.getX(), start.getY(), start.getZ()).color(1.0F, 1.0F, 1.0F, 1.0F).next();
                            buffer.vertex(modelMatrix, end.getX(), end.getY(), end.getZ()).color(1.0F, 1.0F, 1.0F, 1.0F).next();
                        });
                        profiler.pop();
                    });
                }
            }
            matrixStack.pop();
        }
    }
}
