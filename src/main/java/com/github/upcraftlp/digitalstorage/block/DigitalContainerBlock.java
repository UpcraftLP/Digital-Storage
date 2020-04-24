package com.github.upcraftlp.digitalstorage.block;

import com.github.upcraftlp.digitalstorage.blockentity.container.DSContainer;
import com.github.upcraftlp.digitalstorage.util.DigitalContainerProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class DigitalContainerBlock extends DigitalBlock implements BlockEntityProvider {

    public DigitalContainerBlock(Block.Settings settings) {
        super(settings);
    }

    @Deprecated
    @Override
    public void onBlockRemoved(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if(state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if(blockEntity instanceof Inventory) {
                ItemScatterer.spawn(world, pos, (Inventory) blockEntity);
                world.updateHorizontalAdjacent(pos, this);
            }
            super.onBlockRemoved(state, world, pos, newState, moved);
        }
    }

    @Deprecated
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient) {
            BlockEntity be = world.getBlockEntity(pos);
            if(be instanceof DigitalContainerProvider) {
                ((DigitalContainerProvider<? extends DSContainer<?>>) be).openContainer(state, world, pos, player, hand, hit);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Deprecated
    @Override
    public boolean onBlockAction(BlockState state, World world, BlockPos pos, int type, int data) {
        super.onBlockAction(state, world, pos, type, data);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity != null && blockEntity.onBlockAction(type, data);
    }
}
