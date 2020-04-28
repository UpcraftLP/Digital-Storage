package com.github.upcraftlp.digitalstorage.block;

import com.github.glasspane.mesh.api.registry.ItemBlockProvider;
import com.github.glasspane.mesh.api.registry.RenderLayerProvider;
import com.github.upcraftlp.digitalstorage.DigitalStorage;
import com.github.upcraftlp.digitalstorage.util.DSTags;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlacementEnvironment;
import net.minecraft.block.BlockState;
import net.minecraft.block.ConnectingBlock;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import org.jetbrains.annotations.Nullable;

// keeping this for reference, even tho it's not being used right now
public class CableBlock extends ConnectingBlock implements RenderLayerProvider, ItemBlockProvider {

    public CableBlock(Settings settings) {
        super(0.125F, settings); //TODO adjust value
        BlockState state = this.getStateManager().getDefaultState();
        for(Direction facing : FACING_PROPERTIES.keySet()) {
            state = state.with(FACING_PROPERTIES.get(facing), false);
        }
        this.setDefaultState(state);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public RenderLayer getRenderLayer() {
        return RenderLayer.getTranslucent();
    }

    @Deprecated
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos) {
        return super.getStateForNeighborUpdate(state, facing, neighborState, world, pos, neighborPos).with(FACING_PROPERTIES.get(facing), canConnect(neighborState));
    }

    @Deprecated
    @Override
    public boolean canPlaceAtSide(BlockState world, BlockView view, BlockPos pos, BlockPlacementEnvironment env) {
        return false;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.withConnectionProperties(this.getDefaultState(), ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    public BlockState withConnectionProperties(BlockState state, BlockView view, BlockPos pos) {
        for(Direction facing : FACING_PROPERTIES.keySet()) {
            state = state.with(FACING_PROPERTIES.get(facing), canConnect(view.getBlockState(pos.offset(facing))));
        }
        return state;
    }

    public boolean canConnect(BlockState state) {
        return state.getBlock() == this || state.matches(DSTags.BLOCK_CABLES);
    }

    @Nullable
    @Override
    public Item createItem() {
        return new BlockItem(this, new Item.Settings().group(DigitalStorage.DS_ITEMS));
    }
}
