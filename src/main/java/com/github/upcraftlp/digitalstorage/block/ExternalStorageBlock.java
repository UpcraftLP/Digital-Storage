package com.github.upcraftlp.digitalstorage.block;

import com.github.upcraftlp.digitalstorage.api.component.DigitalNetworkPoint;
import com.github.upcraftlp.digitalstorage.util.DSComponents;
import com.github.upcraftlp.digitalstorage.util.ItemStackWrapper;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.component.BlockComponentProvider;
import nerdhub.cardinal.components.api.component.Component;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.PacketByteBuf;
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
        return componentType == DSComponents.NETWORK_COMPONENT && direction != blockView.getBlockState(blockPos).get(FACING);
    }

    @SuppressWarnings("all")
    @Nullable
    @Override
    public <T extends Component> T getComponent(BlockView blockView, BlockPos blockPos, ComponentType<T> componentType, @Nullable Direction direction) {
        return hasComponent(blockView, blockPos, componentType, direction) ? (T) new DigitalNetworkPoint() {
            @Override
            public void fromTag(CompoundTag tag) {
                //NO-OP
            }

            @Override
            public CompoundTag toTag(CompoundTag tag) {
                //NO-OP
                return tag;
            }

            @Override
            public void sync() {
                //NO-OP
            }

            @Override
            public void syncWith(ServerPlayerEntity player) {
                //NO-OP
            }

            @Override
            public void processPacket(PacketContext ctx, PacketByteBuf buf) {
                //NO-OP
            }

            @Override
            public Set<BlockPos> getConnections() {
                return Collections.emptySet();
            }

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
            public BlockPos getPosition() {
                return blockPos;
            }

            @Override
            public World getWorld() {
                return blockView instanceof World ? (World) blockView : null;
            }

        } : null;
    }

    @Override
    public Set<ComponentType<?>> getComponentTypes(BlockView blockView, BlockPos blockPos, @Nullable Direction direction) {
        return null;
    }
}
