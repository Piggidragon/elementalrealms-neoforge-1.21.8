package de.piggidragon.elementalrealms.features;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.features.config.PortalConfiguration;
import de.piggidragon.elementalrealms.features.custom.PortalSpawnFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(Registries.FEATURE, ElementalRealms.MODID);

    public static final Supplier<Feature<PortalConfiguration>> PORTAL_FEATURE =
            FEATURES.register("portal_feature", () ->
                    new PortalSpawnFeature(PortalConfiguration.CODEC));


    /**
     * Registers all mod features with the mod event bus.
     *
     * @param bus The mod's event bus for registration
     */
    public static void register(IEventBus bus) {
        FEATURES.register(bus);
    }
}
