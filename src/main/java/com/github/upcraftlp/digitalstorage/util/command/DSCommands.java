package com.github.upcraftlp.digitalstorage.util.command;

import com.github.upcraftlp.digitalstorage.DigitalStorage;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class DSCommands {

    public static void init() {
        CommandRegistry.INSTANCE.register(false, serverCommandSourceCommandDispatcher -> {
            LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal(DigitalStorage.NAMESPACE);
            ClearNetworkDataCommand.register(builder);
            serverCommandSourceCommandDispatcher.register(builder);
            serverCommandSourceCommandDispatcher.register(CommandManager.literal("ds").redirect(serverCommandSourceCommandDispatcher.getRoot().getChild(DigitalStorage.NAMESPACE)));
        });
    }
}
