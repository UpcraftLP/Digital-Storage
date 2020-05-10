package com.github.upcraftlp.digitalstorage.item;

import com.github.upcraftlp.digitalstorage.DigitalStorage;
import com.github.upcraftlp.digitalstorage.util.DSComponents;
import com.github.upcraftlp.digitalstorage.network.DSNetworkHooks;
import nerdhub.cardinal.components.api.component.BlockComponentProvider;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class LinkingItem extends Item {

    private static final String KEY_LINK_POS = "LinkPos";

    public LinkingItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(!context.getWorld().isClient) {
            ServerWorld world = (ServerWorld) context.getWorld();
            BlockPos pos = context.getBlockPos();
            BlockState state = world.getBlockState(pos);
            BlockComponentProvider.get(state).optionally(world, pos, DSComponents.NETWORK_COMPONENT, context.getSide())
                    .ifPresent(networkPoint -> {
                        ItemStack stack = context.getStack();
                        CompoundTag tag = stack.getOrCreateSubTag(DigitalStorage.NAMESPACE);
                        if(tag.contains(KEY_LINK_POS, NbtType.COMPOUND) && tag.contains("Side", NbtType.STRING)) {
                            BlockPos link = NbtHelper.toBlockPos(tag.getCompound(KEY_LINK_POS));
                            Direction targetSide = Direction.byName(tag.getString("Side"));
                            BlockState targetState = world.getBlockState(link);
                            BlockComponentProvider.get(targetState).optionally(world, link, DSComponents.NETWORK_COMPONENT, targetSide)
                                    .ifPresent(linkTarget -> {
                                        DSNetworkHooks.connect(world, pos, context.getSide(), link, targetSide);
                                        tag.remove(KEY_LINK_POS);
                                        tag.remove("Side");
                                        if(context.getPlayer() != null) {
                                            context.getPlayer().sendMessage(new LiteralText("Connected!"));
                                        }
                                    });
                        }
                        else {
                            tag.put(KEY_LINK_POS, NbtHelper.fromBlockPos(pos));
                            tag.putString("Side", context.getSide().getName());
                        }
                    });
        }
        return ActionResult.SUCCESS;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        CompoundTag tag = stack.getSubTag(DigitalStorage.NAMESPACE);
        if(tag != null && tag.contains(KEY_LINK_POS, NbtType.COMPOUND)) {
            BlockPos pos = NbtHelper.toBlockPos(tag.getCompound(KEY_LINK_POS));
            tooltip.add(new LiteralText("Linking to: " + pos));
        }
    }
}
