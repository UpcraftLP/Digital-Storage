package com.github.upcraftlp.digitalstorage.block;

import com.github.upcraftlp.digitalstorage.blockentity.AccessTerminalBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

import javax.annotation.Nullable;

public class AccessTerminalBlock extends DigitalContainerBlock {

    public AccessTerminalBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return new AccessTerminalBlockEntity();
    }
}
