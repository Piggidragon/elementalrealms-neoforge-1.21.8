package de.piggidragon.elementalrealms.features;

import de.piggidragon.elementalrealms.ElementalRealms;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModFeatures {
    // In ModFeatures
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(Registries.FEATURE, ElementalRealms.MODID);

    public static final Supplier<Feature<NoneFeatureConfiguration>> PORTAL_FEATURE =
            FEATURES.register("portal_feature", () ->
                    new PortalSpawnFeature(NoneFeatureConfiguration.CODEC));

    /**
     * Registers all mod features with the mod event bus.
     *
     * @param bus The mod's event bus for registration
     */
    public static void register(IEventBus bus) {
        FEATURES.register(bus);
    }
}
