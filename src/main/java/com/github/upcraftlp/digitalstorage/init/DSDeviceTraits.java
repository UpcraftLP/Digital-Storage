package com.github.upcraftlp.digitalstorage.init;

import com.github.glasspane.mesh.api.annotation.AutoRegistry;
import com.github.glasspane.mesh.api.registry.AutoRegistryHook;
import com.github.upcraftlp.digitalstorage.DigitalStorage;
import com.github.upcraftlp.digitalstorage.api.network.devices.DeviceTrait;

@AutoRegistry(value = DeviceTrait.class, modid = DigitalStorage.MODID, registry = "digital_storage:device_trait")
public class DSDeviceTraits implements AutoRegistryHook {
}
