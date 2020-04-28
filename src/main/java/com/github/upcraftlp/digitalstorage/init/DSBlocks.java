package com.github.upcraftlp.digitalstorage.init;

import com.github.glasspane.mesh.api.annotation.AutoRegistry;
import com.github.glasspane.mesh.api.registry.AutoRegistryHook;
import com.github.upcraftlp.digitalstorage.DigitalStorage;
import com.github.upcraftlp.digitalstorage.block.AccessTerminalBlock;
import com.github.upcraftlp.digitalstorage.block.DigitalBlock;
import com.github.upcraftlp.digitalstorage.block.DriveBayBlock;
import com.github.upcraftlp.digitalstorage.block.ExternalStorageBlock;
import com.github.upcraftlp.digitalstorage.block.RouterBlock;
import net.minecraft.block.Block;

import static com.github.upcraftlp.digitalstorage.util.DSUtils.*;

@AutoRegistry(value = Block.class, modid = DigitalStorage.MODID, registry = "block")
public class DSBlocks implements AutoRegistryHook {

    public static final Block DRIVE_BAY = makeBlock(DriveBayBlock::new);
    public static final Block TAPE_DRIVE = makeBlock(DigitalBlock::new);
    public static final Block ACCESS_TERMINAL = makeBlock(AccessTerminalBlock::new);
    public static final Block EXTERNAL_STORAGE_ATTACHMENT = makeBlock(ExternalStorageBlock::new);
    public static final Block ROUTER = makeBlock(RouterBlock::new);
}
