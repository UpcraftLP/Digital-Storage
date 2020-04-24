package com.github.upcraftlp.digitalstorage.block;

import com.github.glasspane.mesh.api.registry.ItemBlockProvider;
import com.github.upcraftlp.digitalstorage.DigitalStorage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

import javax.annotation.Nullable;

public class DigitalBlock extends Block implements ItemBlockProvider {

    public DigitalBlock(Block.Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public Item createItem() {
        return new BlockItem(this, new Item.Settings().group(DigitalStorage.DS_ITEMS));
    }

    @Deprecated
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return super.getRenderType(state);
    }
}
