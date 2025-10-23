package de.piggidragon.elementalrealms.rarities;

import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Rarity;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

import java.util.function.UnaryOperator;

public class ModRarities {
    public static final EnumProxy<Rarity> LEGENDARY = new EnumProxy<>(
            Rarity.class,
            -1,
            "elemtentalrealms:legendary",
            (UnaryOperator<Style>) style -> style.withColor(0xFFD700).withBold(true)
    );

    public static final EnumProxy<Rarity> MYTHIC = new EnumProxy<>(
            Rarity.class,
            -1,
            "elemtentalrealms:mythic",
            (UnaryOperator<Style>) style -> style.withColor(0xFF00FF).withBold(true).withItalic(true)
    );

}
