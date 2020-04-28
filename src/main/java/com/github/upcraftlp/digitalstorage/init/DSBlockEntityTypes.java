package com.github.upcraftlp.digitalstorage.init;

import com.github.glasspane.mesh.api.annotation.AutoRegistry;
import com.github.glasspane.mesh.api.registry.AutoRegistryHook;
import com.github.upcraftlp.digitalstorage.DigitalStorage;
import com.github.upcraftlp.digitalstorage.blockentity.AccessTerminalBlockEntity;
import com.github.upcraftlp.digitalstorage.blockentity.DriveBayBlockEntity;
import com.github.upcraftlp.digitalstorage.blockentity.RouterBlockEntity;
import net.minecraft.block.entity.BlockEntityType;

@AutoRegistry(value = BlockEntityType.class, modid = DigitalStorage.MODID, registry = "block_entity_type")
public class DSBlockEntityTypes implements AutoRegistryHook {

    public static final BlockEntityType<AccessTerminalBlockEntity> ACCESS_TERMINAL = BlockEntityType.Builder.create(AccessTerminalBlockEntity::new, DSBlocks.ACCESS_TERMINAL).build(null);
    public static final BlockEntityType<DriveBayBlockEntity> DRIVE_BAY = BlockEntityType.Builder.create(DriveBayBlockEntity::new, DSBlocks.DRIVE_BAY).build(null);
    public static final BlockEntityType<RouterBlockEntity> ROUTER = BlockEntityType.Builder.create(RouterBlockEntity::new, DSBlocks.ROUTER).build(null);

}
