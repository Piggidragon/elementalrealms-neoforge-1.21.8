package de.piggidragon.elementalrealms.advancements;

import de.piggidragon.elementalrealms.items.magic.affinities.AffinityItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;
import java.util.function.Consumer;

public class AdvancementGenerator implements AdvancementSubProvider {

    @Override
    public void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer) {
        Advancement.Builder.advancement()
                .display(
                        new ItemStack(AffinityItems.AFFINITY_STONE_SPACE.get()),
                        Component.translatable("advancements.elementalrealms.root.title"),
                        Component.translatable("advancements.elementalrealms.root.description"),
                        ResourceLocation.fromNamespaceAndPath("elementalrealms", "gui/advancements/backgrounds/elementalrealms"),
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
    }
}