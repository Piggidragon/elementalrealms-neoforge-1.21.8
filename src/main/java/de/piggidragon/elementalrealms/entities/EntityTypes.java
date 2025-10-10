package de.piggidragon.elementalrealms.entities;

import de.piggidragon.elementalrealms.ElementalRealms;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class EntityTypes {
    public static final DeferredRegister.Entities ENTITY_TYPES =
            DeferredRegister.createEntities(ElementalRealms.MODID);

    public static final Supplier<EntityType<PortalEntity>> PORTAL_ENTITY = ENTITY_TYPES.register(
            "portal_entity",
            () -> EntityType.Builder.of(
                            PortalEntity::new,
                            net.minecraft.world.entity.MobCategory.MISC
                    )
                    .sized(1.0f, 2.0f)
                    .fireImmune()
                    .clientTrackingRange(8)
                    .canSpawnFarFromPlayer()
                    .build(ResourceKey.create(Registries.ENTITY_TYPE,
                            ResourceLocation.fromNamespaceAndPath("elemental_realms", "portal_entity")))
    );
}
