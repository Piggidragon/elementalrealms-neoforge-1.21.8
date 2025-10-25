package de.piggidragon.elementalrealms.entities;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.entities.custom.PortalEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

/**
 * Registry for custom entity types.
 */
public class ModEntities {
    /**
     * Deferred register for entity types
     */
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, ElementalRealms.MODID);

    /**
     * Portal entity for dimensional teleportation.
     * Size: 1x2 blocks, fire immune, 8 chunk tracking range.
     */
    public static final Supplier<EntityType<PortalEntity>> PORTAL_ENTITY = ENTITY_TYPES.register(
            "portal_entity",
            () -> EntityType.Builder.of(
                            (EntityType<PortalEntity> type, Level level) -> new PortalEntity(type, level),
                            MobCategory.MISC // Not a living creature
                    )
                    .sized(1.0f, 2.0f) // Hitbox dimensions
                    .fireImmune() // Cannot be destroyed by fire/lava
                    .clientTrackingRange(8) // How far away clients can see this entity
                    .canSpawnFarFromPlayer() // Allows spawning in unloaded areas
                    .build(ResourceKey.create(
                            Registries.ENTITY_TYPE,
                            ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "portal_entity")
                    ))
    );

    /**
     * Registers all entity types with the mod event bus.
     *
     * @param bus The mod's event bus for registration
     */
    public static void register(IEventBus bus) {
        ENTITY_TYPES.register(bus);
    }
}
