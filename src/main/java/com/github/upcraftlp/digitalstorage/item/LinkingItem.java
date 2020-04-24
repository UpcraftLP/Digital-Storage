package com.github.upcraftlp.digitalstorage.item;

import com.github.upcraftlp.digitalstorage.DigitalStorage;
import com.github.upcraftlp.digitalstorage.blockentity.DigitalBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class LinkingItem extends DSItem {

    private static final String KEY_LINK_POS = "LinkPos";

    public LinkingItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockEntity be = context.getWorld().getBlockEntity(context.getBlockPos());
        if(be instanceof DigitalBlockEntity) {
            if(!context.getWorld().isClient()) {
                ItemStack stack = context.getStack();
                CompoundTag tag = stack.getOrCreateSubTag(DigitalStorage.MODID);
                if(tag.contains(KEY_LINK_POS, NbtType.COMPOUND)) {
                    BlockPos link = NbtHelper.toBlockPos(tag.getCompound(KEY_LINK_POS));
                    DigitalBlockEntity<?> blockEntity = (DigitalBlockEntity<?>) be;
                    BlockEntity original = context.getWorld().getBlockEntity(link);
                    if(original instanceof DigitalBlockEntity) {
                        blockEntity.setLink(link);
                        ((DigitalBlockEntity<?>) original).setLink(blockEntity.getPos());
                        tag.remove(KEY_LINK_POS);
                    }
                    else {
                        if(context.getPlayer() != null) {
                            context.getPlayer().sendMessage(new LiteralText("invalid connection!"));
                        }
                    }
                }
                else {
                    tag.put(KEY_LINK_POS, NbtHelper.fromBlockPos(be.getPos()));
                    ((DigitalBlockEntity<?>) be).clearLink();
                }
            }
            return ActionResult.SUCCESS;
        }
        return super.useOnBlock(context);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        CompoundTag tag = stack.getSubTag(DigitalStorage.MODID);
        if(tag != null && tag.contains(KEY_LINK_POS, NbtType.COMPOUND)) {
            BlockPos pos = NbtHelper.toBlockPos(tag.getCompound(KEY_LINK_POS));
            tooltip.add(new LiteralText("Linking to: " + pos));
        }
    }
}
