package com.github.upcraftlp.digitalstorage.init;

import com.github.glasspane.mesh.api.annotation.AutoRegistry;
import com.github.glasspane.mesh.api.registry.AutoRegistryHook;
import com.github.upcraftlp.digitalstorage.DigitalStorage;
import com.github.upcraftlp.digitalstorage.item.LinkingItem;
import net.minecraft.item.Item;

import static com.github.upcraftlp.digitalstorage.util.DSUtils.*;

@AutoRegistry(value = Item.class, modid = DigitalStorage.MODID, registry = "item")
public class DSItems implements AutoRegistryHook {

    public static final Item HDD_1TB = makeDataItem(Item::new);

    //TODO do we want CAT_5, CAT_6, CAT_7?
    public static final Item NETWORK_CABLE = makeItem(LinkingItem::new);
}
