package com.github.upcraftlp.digitalstorage;

import com.github.glasspane.mesh.api.annotation.CalledByReflection;
import com.github.glasspane.mesh.api.logging.MeshLoggerFactory;
import com.github.upcraftlp.digitalstorage.util.DSComponents;
import com.github.upcraftlp.digitalstorage.util.DSMenus;
import com.github.upcraftlp.digitalstorage.util.DSTags;
import org.apache.logging.log4j.Logger;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;

@CalledByReflection
public class DigitalStorage implements ModInitializer {

        public static final String MODID = "digital_storage";

        public static final ItemGroup DS_ITEMS = FabricItemGroupBuilder.build(new Identifier(MODID, "items"), () -> new ItemStack(Items.CHEST));
        public static final ItemGroup DS_DRIVES = FabricItemGroupBuilder.build(new Identifier(MODID, "storage"), () -> new ItemStack(Items.MUSIC_DISC_WAIT));
        private static final Logger logger = MeshLoggerFactory.createPrefixLogger(MODID, "Digital Storage");

        public static Logger getLogger() {
                return logger;
        }

        @Override
        public void onInitialize() {
                DSMenus.registerContainers();
                DSTags.init();
                DSComponents.init();
        }
}