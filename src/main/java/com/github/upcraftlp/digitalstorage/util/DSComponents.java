package com.github.upcraftlp.digitalstorage.util;

import com.github.upcraftlp.digitalstorage.DigitalStorage;
import com.github.upcraftlp.digitalstorage.api.component.DigitalNetworkPoint;
import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import net.minecraft.util.Identifier;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

public class DSComponents {

    public static final ComponentType<DigitalNetworkPoint> NETWORK_COMPONENT = ComponentRegistry.INSTANCE.registerIfAbsent(new Identifier(DigitalStorage.MODID, "network_point"), DigitalNetworkPoint.class);

    public static void init() {
        String fields = Arrays.stream(DSComponents.class.getFields()).map(Field::getName).map(s -> s.toLowerCase(Locale.ROOT)).sorted().collect(Collectors.joining("\n"));
        DigitalStorage.getLogger().trace("loading component types:\n{}", fields);
    }
}
