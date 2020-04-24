package com.github.upcraftlp.digitalstorage.blockentity;

import com.github.upcraftlp.digitalstorage.DigitalStorage;
import com.github.upcraftlp.digitalstorage.api.component.DigitalNetworkPoint;
import com.github.upcraftlp.digitalstorage.api.component.LinkHolder;
import com.github.upcraftlp.digitalstorage.blockentity.container.DSContainer;
import com.github.upcraftlp.digitalstorage.util.DSComponents;
import com.github.upcraftlp.digitalstorage.util.DigitalContainerProvider;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.component.BlockComponentProvider;
import nerdhub.cardinal.components.api.component.Component;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public abstract class DigitalBlockEntity<T extends DSContainer<?>> extends BlockEntity implements DigitalContainerProvider<T>, BlockComponentProvider {

    protected final LinkHolder link = this.createLinkHolder();

    protected DigitalNetworkPoint createLinkHolder() {
        return new LinkHolder();
    }

    @Nullable
    private BlockPos link;

    public DigitalBlockEntity(BlockEntityType<?> type) {
        super(type);
    }

    public boolean hasLink() {
        return link != null;
    }

    public void clearLink() {
        this.setLink(null);
    }

    @NotNull
    public BlockPos getLink() {
        return Objects.requireNonNull(link, "getLink() called without checking hasLink()!");
    }

    public void setLink(@Nullable BlockPos link) {
        if(link != null) {
            DigitalStorage.getLogger().trace("Linked BlockEntity at {} to {} (previously linked to {})", this.getPos(), link, this.getLink());
        }
        else {
            DigitalStorage.getLogger().trace("Removed Link from BLockEntity at {} (previously linked to {})", this.getPos(), this.getLink());
        }
        this.link = link;
        this.markDirty();
    }

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        if(tag.contains("LinkPos", NbtType.COMPOUND)) {
            this.link = NbtHelper.toBlockPos(tag.getCompound("LinkPos"));
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        if(this.link != null) {
            tag.put("LinkPos", NbtHelper.fromBlockPos(this.link));
        }
        return super.toTag(tag);
    }

    //TODO stats
    @Override
    public Identifier getOpenStat() {
        return Stats.OPEN_CHEST; //Stats.CUSTOM.getOrCreateStat(Registry.BLOCK_ENTITY_TYPE.getId(this.getType())).getValue();
    }

    @Override
    public <C extends Component> boolean hasComponent(BlockView blockView, BlockPos pos, ComponentType<C> type, @Nullable Direction side) {
        return type == DSComponents.NETWORK_COMPONENT;
    }

    @Nullable
    @Override
    public <C extends Component> C getComponent(BlockView blockView, BlockPos pos, ComponentType<C> type, @Nullable Direction side) {
        return type == DSComponents.NETWORK_COMPONENT ? : null;
    }

    @Override
    public Set<ComponentType<?>> getComponentTypes(BlockView blockView, BlockPos pos, @Nullable Direction side) {
        return Collections.singleton(DSComponents.NETWORK_COMPONENT);
    }
}
