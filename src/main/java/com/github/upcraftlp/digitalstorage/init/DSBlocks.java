package com.github.upcraftlp.digitalstorage.init;

import com.github.glasspane.mesh.api.annotation.AutoRegistry;
import com.github.glasspane.mesh.api.registry.AutoRegistryHook;
import com.github.upcraftlp.digitalstorage.DigitalStorage;
import com.github.upcraftlp.digitalstorage.block.AccessTerminalBlock;
import com.github.upcraftlp.digitalstorage.block.CableBlock;
import com.github.upcraftlp.digitalstorage.block.DigitalBlock;
import com.github.upcraftlp.digitalstorage.block.DriveBayBlock;
import com.github.upcraftlp.digitalstorage.block.ExternalStorageBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;

@AutoRegistry(value = Block.class, modid = DigitalStorage.MODID, registry = "block")
public class DSBlocks implements AutoRegistryHook {

    public static final Block CABLE = new CableBlock(Block.Settings.of(Material.METAL).nonOpaque());
    public static final Block DRIVE_BAY = new DriveBayBlock(Block.Settings.of(Material.METAL));
    public static final Block TAPE_DRIVE = new DigitalBlock(Block.Settings.of(Material.METAL));
    public static final Block ACCESS_TERMINAL = new AccessTerminalBlock(Block.Settings.of(Material.METAL));
    public static final Block EXTERNAL_STORAGE_ATTACHMENT = new ExternalStorageBlock(Block.Settings.of(Material.METAL).nonOpaque());
}
