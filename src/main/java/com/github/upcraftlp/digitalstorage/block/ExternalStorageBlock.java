package com.github.upcraftlp.digitalstorage.block;

import com.github.upcraftlp.digitalstorage.api.DigitalComponents;
import com.github.upcraftlp.digitalstorage.api.network.NetworkPoint;
import com.github.upcraftlp.digitalstorage.util.ItemStackWrapper;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.component.BlockComponentProvider;
import nerdhub.cardinal.components.api.component.Component;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ExternalStorageBlock extends DigitalBlock implements BlockComponentProvider {

    public static final DirectionProperty FACING = Properties.FACING;

    public ExternalStorageBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    public <T extends Component> boolean hasComponent(BlockView blockView, BlockPos blockPos, ComponentType<T> componentType, @Nullable Direction direction) {
        return componentType == DigitalComponents.NETWORK_ACCESSIBLE && direction != blockView.getBlockState(blockPos).get(FACING);
    }

    @SuppressWarnings("all")
    @Nullable
    @Override
    public <T extends Component> T getComponent(BlockView blockView, BlockPos blockPos, ComponentType<T> componentType, @Nullable Direction direction) {
        return hasComponent(blockView, blockPos, componentType, direction) ? (T) new NetworkPoint() {
            @Override
            public Collection<ItemStackWrapper> getContentsTemp() {
                BlockState thisState = blockView.getBlockState(blockPos);
                BlockPos neighborPos = blockPos.offset(thisState.get(FACING));
                if(blockView instanceof World) {
                    Inventory inventory = HopperBlockEntity.getInventoryAt((World) blockView, neighborPos);
                    if(inventory != null) {
                        List<ItemStackWrapper> content = new ArrayList<>(inventory.getInvSize());
                        for(int i = 0; i < inventory.getInvSize(); i++) {
                            content.add(new ItemStackWrapper(inventory.getInvStack(i).copy()));
                        }
                        return content;
                    }
                }
                return Collections.emptyList();
            }

            @Override
            public UUID getDeviceID() {
                return null;
            }

            @Override
            public UUID getNetworkID() {
                return null;
            }

            @Override
            public Collection<NetworkPoint> getConnections() {
                return null;
            }
        } : null;
    }

    @Override
    public Set<ComponentType<?>> getComponentTypes(BlockView blockView, BlockPos blockPos, @Nullable Direction direction) {
        return null;
    }
}
