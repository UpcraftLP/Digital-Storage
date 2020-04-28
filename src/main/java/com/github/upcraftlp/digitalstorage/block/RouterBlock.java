package com.github.upcraftlp.digitalstorage.block;

import com.github.upcraftlp.digitalstorage.blockentity.RouterBlockEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

import javax.annotation.Nullable;

public class RouterBlock extends DigitalBlock implements BlockEntityProvider {

    public RouterBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return new RouterBlockEntity();
    }
}
