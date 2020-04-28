package com.github.upcraftlp.digitalstorage.blockentity;

import com.github.upcraftlp.digitalstorage.api.component.LinkHolder;
import com.github.upcraftlp.digitalstorage.menu.container.DSContainer;
import com.github.upcraftlp.digitalstorage.util.DSComponents;
import com.github.upcraftlp.digitalstorage.menu.DigitalContainerProvider;
import com.github.upcraftlp.digitalstorage.util.ItemStackWrapper;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.component.BlockComponentProvider;
import nerdhub.cardinal.components.api.component.Component;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public abstract class DigitalBlockEntity<T extends DSContainer<?>> extends BlockEntity implements DigitalContainerProvider<T>, BlockComponentProvider, BlockEntityClientSerializable {

    protected final LinkHolder link = this.createLinkHolder();

    @Nonnull
    protected LinkHolder createLinkHolder() {
        return new LinkHolder(this);
    }

    public DigitalBlockEntity(BlockEntityType<?> type) {
        super(type);
    }

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        this.link.fromTag(tag.getCompound("Link"));
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.put("Link", this.link.toTag(new CompoundTag()));
        return super.toTag(tag);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        return this.toTag(tag);
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        this.fromTag(tag);
    }

    //TODO stats
    @Override
    public Identifier getOpenStat() {
        return Stats.OPEN_CHEST; //Stats.CUSTOM.getOrCreateStat(Registry.BLOCK_ENTITY_TYPE.getId(this.getType())).getValue();
    }

    //FIXME get rid of this
    public Collection<ItemStackWrapper> getContentsTemp() {
        return Collections.emptySet();
    }

    @Override
    public <C extends Component> boolean hasComponent(BlockView blockView, BlockPos pos, ComponentType<C> type, @Nullable Direction side) {
        return type == DSComponents.NETWORK_COMPONENT;
    }

    @Nullable
    @Override
    public <C extends Component> C getComponent(BlockView blockView, BlockPos pos, ComponentType<C> type, @Nullable Direction side) {
        return type == DSComponents.NETWORK_COMPONENT ? (C) this.link: null;
    }

    @Override
    public Set<ComponentType<?>> getComponentTypes(BlockView blockView, BlockPos pos, @Nullable Direction side) {
        return Collections.singleton(DSComponents.NETWORK_COMPONENT);
    }
}
