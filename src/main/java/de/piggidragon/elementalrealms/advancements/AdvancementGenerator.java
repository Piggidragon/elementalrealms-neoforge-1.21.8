package de.piggidragon.elementalrealms.advancements;

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
import net.minecraft.world.item.Items;

import java.util.Optional;
import java.util.function.Consumer;

public class AdvancementGenerator implements AdvancementSubProvider {

    @Override
    public void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer) {
        Advancement.Builder.advancement()
                .display(
                        new ItemStack(Items.DRAGON_HEAD),
                        Component.translatable("advancements.elemntalrealms.root.title"),
                        Component.translatable("advancements.elemntalrealms.root.description"),
                        ResourceLocation.fromNamespaceAndPath("minecraft", "textures/gui/advancements/backgrounds/end.png"),
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