package com.github.upcraftlp.digitalstorage.api.network.devices;

import com.github.upcraftlp.digitalstorage.init.DSRegistries;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Objects;

public interface DeviceTrait {
    default Identifier getTypeID() {
        return Objects.requireNonNull(DeviceTrait.getRegistry().getId(this), "device trait not registered: " + this.getClass());
    }

    static Registry<DeviceTrait> getRegistry() {
        return DSRegistries.DEVICE_TRAIT;
    }
}
