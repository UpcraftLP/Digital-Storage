package com.github.upcraftlp.digitalstorage.mixin.client;

import com.github.upcraftlp.digitalstorage.DigitalStorage;
import com.github.upcraftlp.digitalstorage.client.render.NetworkConnectionRenderer;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.Matrix4f;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author biom4st3r
 * code copied with permission
 * see "https://gitlab.com/biom4st3r/dynocaps/-/blob/987bc9e7c41d720fed76d6d2aa8a469a0ddbc69b/src/main/java/com/biom4st3r/dynocaps/mixin/WorldRendererMixin.java"
 */
@Environment(EnvType.CLIENT)
@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {

    @Shadow
    @Final
    private BufferBuilderStorage bufferBuilders;

    @Shadow
    private ClientWorld world;

    @Shadow @Final private ObjectList<WorldRenderer.ChunkInfo> visibleChunks;

    @Inject(method = "render", at = @At(value = "INVOKE_STRING", target = "net/minecraft/util/profiler/Profiler.swap(Ljava/lang/String;)V", args = "ldc=blockentities", shift = At.Shift.BEFORE))
    private void ds_onRenderWorld(MatrixStack matrixStack, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f modelMatrix, CallbackInfo ci) {
        Profiler profiler = world.getProfiler();
        Vec3d cameraPos = camera.getPos();
        profiler.swap(DigitalStorage.MODID + ":networks");
        NetworkConnectionRenderer.render(matrixStack, profiler, tickDelta, this.bufferBuilders.getEntityVertexConsumers(), cameraPos, this.visibleChunks);
    }
}

