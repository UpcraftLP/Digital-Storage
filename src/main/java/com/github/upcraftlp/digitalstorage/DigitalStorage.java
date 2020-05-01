package com.github.upcraftlp.digitalstorage;

import com.github.glasspane.mesh.api.annotation.CalledByReflection;
import com.github.glasspane.mesh.api.logging.MeshLoggerFactory;
import com.github.upcraftlp.digitalstorage.menu.DSMenus;
import com.github.upcraftlp.digitalstorage.network.packet.ChunkInfoS2CPacket;
import com.github.upcraftlp.digitalstorage.network.packet.ClearDataS2CPacket;
import com.github.upcraftlp.digitalstorage.util.DSComponents;
import com.github.upcraftlp.digitalstorage.util.DSTags;
import com.github.upcraftlp.digitalstorage.util.command.DSCommands;
import nerdhub.cardinal.components.api.event.ChunkSyncCallback;
import nerdhub.cardinal.components.api.event.PlayerSyncCallback;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ChunkHolder;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ChunkPos;
import org.apache.logging.log4j.Logger;

import java.util.Set;
import java.util.stream.Collectors;

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
        DSCommands.init();
        PlayerSyncCallback.EVENT.register(player -> {
            ThreadedAnvilChunkStorage chunkStorage = player.getServerWorld().getChunkManager().threadedAnvilChunkStorage;
            Set<ChunkPos> toSync = chunkStorage.currentChunkHolders.values().stream()
                    .map(ChunkHolder::getPos)
                    .filter(pos -> ThreadedAnvilChunkStorage.getChebyshevDistance(pos, player, true) <= chunkStorage.watchDistance)
                    .collect(Collectors.toSet());
            ClearDataS2CPacket.clearClientData(player, toSync);
        });
        ChunkSyncCallback.EVENT.register((player, chunk) -> ChunkInfoS2CPacket.sendChunkData(player, player.getServerWorld(), chunk));
    }
}
