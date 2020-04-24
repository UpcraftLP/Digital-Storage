package com.github.upcraftlp.digitalstorage.block;

import com.github.upcraftlp.digitalstorage.blockentity.DriveBayBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

import javax.annotation.Nullable;

public class DriveBayBlock extends DigitalContainerBlock {

    public DriveBayBlock(Block.Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return new DriveBayBlockEntity();
    }
}
