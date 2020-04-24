package com.github.upcraftlp.digitalstorage.item;

import com.github.upcraftlp.digitalstorage.DigitalStorage;
import net.minecraft.item.Item;

public class DSItem extends Item {

    public DSItem(Settings settings) {
        super(settings.group(DigitalStorage.DS_ITEMS));
    }
}
