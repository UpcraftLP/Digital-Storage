package com.github.upcraftlp.digitalstorage.util.network;

import net.minecraft.container.Container;
import net.minecraft.network.NetworkSide;
import net.minecraft.util.Identifier;

public class NetworkHooks {

    /**
     * for when you want to do an update regardless of matching {@link Container#syncId},
     * or when the container's {@code syncId} is unknown
     */
    public static final int FORCE_SYNC = -1;

    public static Identifier getPacketID(String name, NetworkSide targetSide) {
        return new Identifier("ds", String.format("%s_%s", targetSide == NetworkSide.CLIENTBOUND ? "s2c" : "c2s", name));
    }
}
