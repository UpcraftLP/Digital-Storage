package com.github.upcraftlp.digitalstorage.util;

import com.github.upcraftlp.digitalstorage.DigitalStorage;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.Item;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class DSUtils {

    public static <T extends Item> T makeItem(Function<Item.Settings, T> factory) {
        return makeItem(factory, UnaryOperator.identity());
    }

    public static <T extends Item> T makeItem(Function<Item.Settings, T> factory, UnaryOperator<Item.Settings> additionalSettings) {
        return factory.apply(additionalSettings.apply(new Item.Settings().group(DigitalStorage.DS_ITEMS)));
    }

    public static <T extends Item> T makeDataItem(Function<Item.Settings, T> factory) {
        return makeDataItem(factory, s -> s.group(DigitalStorage.DS_DRIVES).maxCount(1));
    }

    public static <T extends Item> T makeDataItem(Function<Item.Settings, T> factory, UnaryOperator<Item.Settings> additionalSettings) {
        return factory.apply(additionalSettings.apply(new Item.Settings().group(DigitalStorage.DS_DRIVES).maxCount(1)));
    }

    public static <T extends Block> T makeBlock(Function<Block.Settings, T> factory) {
        return makeBlock(factory, UnaryOperator.identity());
    }

    public static <T extends Block> T makeBlock(Function<Block.Settings, T> factory, UnaryOperator<FabricBlockSettings> additionalSettings) {
        FabricBlockSettings settings = FabricBlockSettings
                .of(Material.METAL)
                .breakByTool(FabricToolTags.PICKAXES, 1)
                .strength(1.0F, 1.0F)
                .nonOpaque();
        return factory.apply(additionalSettings.apply(settings).build());
    }
}
