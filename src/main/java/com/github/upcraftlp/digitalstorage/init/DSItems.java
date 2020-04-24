package com.github.upcraftlp.digitalstorage.init;

import com.github.glasspane.mesh.api.annotation.AutoRegistry;
import com.github.glasspane.mesh.api.registry.AutoRegistryHook;
import com.github.upcraftlp.digitalstorage.DigitalStorage;
import com.github.upcraftlp.digitalstorage.item.LinkingItem;
import net.minecraft.item.Item;

@AutoRegistry(value = Item.class, modid = DigitalStorage.MODID, registry = "item")
public class DSItems implements AutoRegistryHook {

    public static final Item HDD_1TB = new Item(new Item.Settings().group(DigitalStorage.DS_DRIVES).maxCount(1));

    public static final Item DEBUG_LINKER = new LinkingItem(new Item.Settings().group(DigitalStorage.DS_ITEMS).maxCount(1));
}
