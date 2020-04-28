package com.github.upcraftlp.digitalstorage.block;

import com.github.glasspane.mesh.api.registry.ItemBlockProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

import javax.annotation.Nullable;

import static com.github.upcraftlp.digitalstorage.util.DSUtils.*;

public class DigitalBlock extends Block implements ItemBlockProvider {

    public DigitalBlock(Block.Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public Item createItem() {
        return makeItem(settings -> new BlockItem(this, settings));
    }

    @Deprecated
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return super.getRenderType(state);
    }
}
