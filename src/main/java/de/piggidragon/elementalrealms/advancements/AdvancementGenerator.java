
package de.piggidragon.elementalrealms.advancements;

import de.piggidragon.elementalrealms.items.magic.affinities.AffinityItems;
import de.piggidragon.elementalrealms.items.magic.dimension.DimensionItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import java.util.function.Consumer;

import java.util.Optional;

public class AdvancementGenerator implements AdvancementSubProvider {

    /**
     * Generates all advancements for the mod's progression tree.
     *
     * @param provider Registry lookup provider for entity types and other registries
     * @param consumer Consumer to register created advancements with the data generator
     */
    @Override
    public void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer) {
        // Root advancement: unlocked by killing the Ender Dragon
        AdvancementHolder rootAdvancement = Advancement.Builder.advancement()
                .display(
                        new ItemStack(AffinityItems.AFFINITY_STONE_SPACE.get()),
                        Component.translatable("advancements.elementalrealms.root.title"),
                        Component.translatable("advancements.elementalrealms.root.description"),
                        ResourceLocation.fromNamespaceAndPath("minecraft", "block/cracked_deepslate_tiles"),
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("killed_dragon",
                        KilledTrigger.TriggerInstance.playerKilledEntity(
                                Optional.of(
                                        EntityPredicate.Builder.entity()
                                                .of(provider.lookupOrThrow(Registries.ENTITY_TYPE), EntityType.ENDER_DRAGON)
                                                .build()
                                )
                        )
                )
                .save(consumer, ResourceLocation.fromNamespaceAndPath("elementalrealms", "root"));

        Advancement.Builder.advancement()
                .parent(rootAdvancement)
                .display(
                        new ItemStack(DimensionItems.DIMENSION_STAFF.get()),
                        Component.translatable("advancements.elementalrealms.get_staff.title"),
                        Component.translatable("advancements.elementalrealms.get_staff.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("has_staff",
                        InventoryChangeTrigger.TriggerInstance.hasItems(DimensionItems.DIMENSION_STAFF.get())
                )
                .save(consumer, ResourceLocation.fromNamespaceAndPath("elementalrealms", "get_staff"));
    }
}