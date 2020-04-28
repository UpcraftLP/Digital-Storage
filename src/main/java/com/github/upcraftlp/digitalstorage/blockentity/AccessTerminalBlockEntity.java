package com.github.upcraftlp.digitalstorage.blockentity;

import com.github.upcraftlp.digitalstorage.api.component.DigitalNetworkPoint;
import com.github.upcraftlp.digitalstorage.menu.container.AccessTerminalContainer;
import com.github.upcraftlp.digitalstorage.init.DSBlockEntityTypes;
import com.github.upcraftlp.digitalstorage.util.DSComponents;
import com.github.upcraftlp.digitalstorage.menu.DSMenus;
import com.github.upcraftlp.digitalstorage.util.ItemStackWrapper;
import nerdhub.cardinal.components.api.component.BlockComponentProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class AccessTerminalBlockEntity extends DigitalBlockEntity<AccessTerminalContainer> {

    private static final Random RANDOM = new Random();

    public AccessTerminalBlockEntity() {
        super(DSBlockEntityTypes.ACCESS_TERMINAL);
    }

    @Override
    public Class<AccessTerminalContainer> getContainerClass() {
        return AccessTerminalContainer.class;
    }

    @Override
    public void onContainerOpened(World world, BlockPos pos, PlayerEntity player, Hand hand, int syncID, AccessTerminalContainer container) {
        //FIXME debug
        CompletableFuture.supplyAsync(() -> {
            Collection<ItemStackWrapper> ret = Collections.emptyList();
            //List<ItemStackWrapper> list = new ArrayList<>(10);
            //for(int j = 0; j < 12; j++) {
            //    Item item;
            //    for(item = Registry.ITEM.getRandom(RANDOM); item == Items.AIR; item = Registry.ITEM.getRandom(RANDOM));
            //    list.add(new ItemStackWrapper(new ItemStack(item), RANDOM.nextInt(60000) + 1));
            //}
            //return list;
            if(this.link.hasConnection()) {
                Optional<DigitalNetworkPoint> ap = BlockComponentProvider.get(world.getBlockState(this.link.getConnection())).optionally(world, this.link.getConnection(), DSComponents.NETWORK_COMPONENT, null);
                ret = ap.map(DigitalNetworkPoint::getContentsTemp).orElse(Collections.emptyList());
            }
            return ret;
        }, world.getServer()).thenAcceptAsync(items -> {
            System.out.println("adding items: " + items.size());
            container.addItems(items, true, false);
        }, world.getServer());
    }

    @Override
    public Identifier getContainerID() {
        return DSMenus.ACCESS_TERMINAL;
    }

    @Override
    public void writeData(PacketByteBuf byteBuf) {
    }
}
