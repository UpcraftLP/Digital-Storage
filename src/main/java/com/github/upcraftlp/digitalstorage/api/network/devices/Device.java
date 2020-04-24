package com.github.upcraftlp.digitalstorage.api.network.devices;

import java.util.Collections;
import java.util.Set;

public interface Device {

    DeviceType getDeviceType();

    default Set<DeviceTrait> getDeviceTraits() {
        return Collections.emptySet();
    }
}
