package com.github.upcraftlp.digitalstorage.util.command;

import com.github.upcraftlp.digitalstorage.api.PersistentNetworkData;
import com.github.upcraftlp.digitalstorage.network.DSNetworkHooks;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.arguments.DimensionArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.dimension.DimensionType;

public class ClearNetworkDataCommand {

    public static LiteralArgumentBuilder<ServerCommandSource> register(LiteralArgumentBuilder<ServerCommandSource> builder) {
        return builder
                .then(CommandManager.literal("clear_networks").requires(source -> source.hasPermissionLevel(4))
                        .then(CommandManager.argument("dimension", DimensionArgumentType.dimension())
                                .executes(context -> {
                                    DimensionType dim = DimensionArgumentType.getDimensionArgument(context, "dimension");
                                    ServerWorld world = context.getSource().getMinecraftServer().getWorld(dim);
                                    return clear(context, world);
                                }))
                        .then(CommandManager.literal("all").executes(context -> {
                            int count = DSNetworkHooks.resetAll(context.getSource().getMinecraftServer());
                            context.getSource().sendFeedback(new TranslatableText("command.digital_storage.clear_networks.success.all", count), true);
                            return count;
                        }))
                        .executes(context -> clear(context, context.getSource().getWorld()))
                );
    }

    private static int clear(CommandContext<ServerCommandSource> ctx, ServerWorld world) {
        int ret = PersistentNetworkData.get(world).debugClearData();
        Identifier dimension = Registry.DIMENSION_TYPE.getId(world.getDimension().getType());
        ctx.getSource().sendFeedback(new TranslatableText("command.digital_storage.clear_networks.success.single", ret, new TranslatableText(String.valueOf(dimension))), true);
        return ret;
    }
}
