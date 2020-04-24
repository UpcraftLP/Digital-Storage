package com.github.upcraftlp.digitalstorage.api;

import com.github.upcraftlp.digitalstorage.DigitalStorage;
import com.github.upcraftlp.digitalstorage.api.network.NetworkPoint;
import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import net.minecraft.util.Identifier;

public final class DigitalComponents {

    public static final ComponentType<NetworkPoint> NETWORK_ACCESSIBLE = ComponentRegistry.INSTANCE.registerIfAbsent(new Identifier(DigitalStorage.MODID, "network_accessible"), NetworkPoint.class);
}
