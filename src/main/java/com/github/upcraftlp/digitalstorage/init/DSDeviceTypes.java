package com.github.upcraftlp.digitalstorage.init;

import com.github.glasspane.mesh.api.annotation.AutoRegistry;
import com.github.glasspane.mesh.api.registry.AutoRegistryHook;
import com.github.upcraftlp.digitalstorage.DigitalStorage;
import com.github.upcraftlp.digitalstorage.api.network.devices.DeviceType;
import com.github.upcraftlp.digitalstorage.impl.DefaultDeviceType;

@AutoRegistry(value = DeviceType.class, modid = DigitalStorage.MODID, registry = "digital_storage:device_type")
public class DSDeviceTypes implements AutoRegistryHook {

    public static final DeviceType DISK_DRIVE = new DefaultDeviceType();
    public static final DeviceType TAPE_DRIVE = new DefaultDeviceType();
    public static final DeviceType SECURITY_SYSTEM = new DefaultDeviceType();
    public static final DeviceType ALARM = new DefaultDeviceType();
    public static final DeviceType UI_INVENTORY = new DefaultDeviceType();
    public static final DeviceType UI_CRAFTING = new DefaultDeviceType();
    public static final DeviceType UI_STATUS_MONITOR = new DefaultDeviceType();
    public static final DeviceType UI_DEVICE_MONITOR = new DefaultDeviceType();
    public static final DeviceType COMPOSTER = new DefaultDeviceType(); //vanilla
    public static final DeviceType DISPENSER = new DefaultDeviceType(); //vanilla
    public static final DeviceType DROPPER = new DefaultDeviceType(); //vanilla
    public static final DeviceType LARGE_ITEM_CONTAINER = new DefaultDeviceType();
    public static final DeviceType ITEM_CONTAINER = new DefaultDeviceType(); //vanilla
    public static final DeviceType SMELTER = new DefaultDeviceType(); //vanilla
    public static final DeviceType ALLOY_SMELTER = new DefaultDeviceType();
    public static final DeviceType COKE_OVEN = new DefaultDeviceType();
    public static final DeviceType CRAFTER = new DefaultDeviceType();
    public static final DeviceType TRASHCAN = new DefaultDeviceType();
    public static final DeviceType GENERATOR = new DefaultDeviceType();
    public static final DeviceType COMPACTOR = new DefaultDeviceType();
    public static final DeviceType CRUSHER = new DefaultDeviceType();
    public static final DeviceType TREETAP = new DefaultDeviceType();
    public static final DeviceType ELECTROLYZER = new DefaultDeviceType();
    public static final DeviceType ROLLING_MACHINE = new DefaultDeviceType();
    public static final DeviceType CANNING_MACHINE = new DefaultDeviceType();
    public static final DeviceType WIRE_MILL = new DefaultDeviceType();
    public static final DeviceType CENTRIFUGE = new DefaultDeviceType();
    public static final DeviceType ITEM_PRODUCER = new DefaultDeviceType();
    public static final DeviceType TRADE_STATION = new DefaultDeviceType();
    public static final DeviceType CHEMICAL_REACTOR = new DefaultDeviceType();
}
