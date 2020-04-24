package com.github.upcraftlp.digitalstorage.api.network.devices;

import com.github.upcraftlp.digitalstorage.init.DSRegistries;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Objects;

public interface DeviceType {

    default Identifier getTypeID() {
        return Objects.requireNonNull(DeviceType.getRegistry().getId(this), "device type not registered: " + this.getClass());
    }

    static Registry<DeviceType> getRegistry() {
        return DSRegistries.DEVICE_TYPE;
    }
}
