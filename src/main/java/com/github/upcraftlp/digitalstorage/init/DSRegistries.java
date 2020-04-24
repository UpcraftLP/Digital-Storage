package com.github.upcraftlp.digitalstorage.init;

import com.github.glasspane.mesh.api.annotation.AutoRegistry;
import com.github.glasspane.mesh.api.registry.AutoRegistryHook;
import com.github.upcraftlp.digitalstorage.DigitalStorage;
import com.github.upcraftlp.digitalstorage.api.network.devices.DeviceTrait;
import com.github.upcraftlp.digitalstorage.api.network.devices.DeviceType;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;

@AutoRegistry(value = Registry.class, modid = DigitalStorage.MODID, registry = "registries")
public class DSRegistries implements AutoRegistryHook {

    public static final Registry<DeviceType> DEVICE_TYPE = new SimpleRegistry<>();

    public static final Registry<DeviceTrait> DEVICE_TRAIT = new SimpleRegistry<>();
}
