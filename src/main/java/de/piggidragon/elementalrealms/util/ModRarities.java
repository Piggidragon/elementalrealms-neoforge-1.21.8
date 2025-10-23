package de.piggidragon.elementalrealms.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Rarity;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

import java.util.function.UnaryOperator;

public class ModRarities {
    public static final EnumProxy<Rarity> LEGENDARY = new EnumProxy<>(
            Rarity.class,
            -1,
            "elementalrealms:legendary",
            (UnaryOperator<Style>) style -> style.withColor(ChatFormatting.GOLD)
    );

    public static final EnumProxy<Rarity> MYTHIC = new EnumProxy<>(
            Rarity.class,
            -1,
            "elementalrealms:mythic",
            (UnaryOperator<Style>) style -> style.withColor(ChatFormatting.DARK_PURPLE)
    );

}
