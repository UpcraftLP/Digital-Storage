package com.github.upcraftlp.digitalstorage.util;

import com.github.upcraftlp.digitalstorage.blockentity.container.DSContainer;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface DigitalContainerProvider<T extends DSContainer<?>> {

    @SuppressWarnings("unchecked")
    default void openContainer(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hitResult) {
        ContainerProviderRegistry.INSTANCE.openContainer(this.getContainerID(), player, byteBuf -> {
            byteBuf.writeBlockPos(pos);
            this.writeData(byteBuf);
        });
        if(getContainerClass().isInstance(player.container)) {
            player.incrementStat(this.getOpenStat());
            this.onContainerOpened(world, pos, player, hand, player.container.syncId, (T) player.container);
        }
    }

    Class<T> getContainerClass();

    default void onContainerOpened(World world, BlockPos pos, PlayerEntity player, Hand hand, int syncID, T container) {

    }

    Identifier getContainerID();

    void writeData(PacketByteBuf byteBuf);

    Identifier getOpenStat();
}
