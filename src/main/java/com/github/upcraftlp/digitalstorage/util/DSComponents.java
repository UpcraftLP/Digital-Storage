package com.github.upcraftlp.digitalstorage.util;

import com.github.upcraftlp.digitalstorage.DigitalStorage;
import com.github.upcraftlp.digitalstorage.api.component.DigitalNetworkPoint;
import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import net.minecraft.util.Identifier;

public class DSComponents {

    public static final ComponentType<DigitalNetworkPoint> NETWORK_COMPONENT = ComponentRegistry.INSTANCE.registerIfAbsent(new Identifier(DigitalStorage.MODID, "network_point"), DigitalNetworkPoint.class);

    public static void init() {
        DigitalStorage.getLogger().trace("loading component types");
    }
}
