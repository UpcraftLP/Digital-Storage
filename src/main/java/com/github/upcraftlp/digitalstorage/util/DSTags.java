package com.github.upcraftlp.digitalstorage.util;

import com.github.upcraftlp.digitalstorage.DigitalStorage;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class DSTags {

    public static final Tag<Block> BLOCK_CABLES = TagRegistry.block(new Identifier(DigitalStorage.NAMESPACE, "cables"));

    public static final Tag<Item> ITEM_HARD_DRIVES = TagRegistry.item(new Identifier(DigitalStorage.NAMESPACE, "storage/hard_drives"));
    public static final Tag<Item> ITEM_TAPE_DRIVES = TagRegistry.item(new Identifier(DigitalStorage.NAMESPACE, "storage/tape_drives"));
    public static final Tag<Item> ITEM_CRYSTAL_DRIVES = TagRegistry.item(new Identifier(DigitalStorage.NAMESPACE, "storage/crystal_drives"));

    public static void init() {
        DigitalStorage.getLogger().trace("registering tags");
    }
}
