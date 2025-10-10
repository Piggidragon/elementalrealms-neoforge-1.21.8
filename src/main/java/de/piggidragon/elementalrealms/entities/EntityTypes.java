package de.piggidragon.elementalrealms.entities;

import de.piggidragon.elementalrealms.ElementalRealms;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class EntityTypes {
    public static final DeferredRegister.Entities ENTITY_TYPES =
            DeferredRegister.createEntities(ElementalRealms.MODID);

    public static final Supplier<EntityType<PortalEntity>> PORTAL_ENTITY = ENTITY_TYPES.register(
            "portal_entity",
            () -> EntityType.Builder.of(PortalEntity::new, MobCategory.MISC)
                    .sized(1.0f, 2.0f)
                    .fireImmune()
                    .clientTrackingRange(8)
                    .canSpawnFarFromPlayer()
                    .build(ResourceKey.create(
                            Registries.ENTITY_TYPE,
                            ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "portal_entity")
                    ))
    );

    public static void register(IEventBus bus) {
        ENTITY_TYPES.register(bus);
    }
}
