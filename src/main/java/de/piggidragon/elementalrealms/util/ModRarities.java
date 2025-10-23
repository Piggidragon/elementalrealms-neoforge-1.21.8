package de.piggidragon.elementalrealms.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Rarity;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

import java.util.function.UnaryOperator;

/**
 * Defines custom rarity tiers beyond Minecraft's default COMMON, UNCOMMON, RARE, and EPIC.
 * Uses enum extension to add new rarity levels with custom colors for affinity items.
 *
 * <p>Custom rarities:</p>
 * <ul>
 *   <li>LEGENDARY (Gold) - For deviant affinity items (Lightning, Ice, Sound, Gravity)</li>
 *   <li>MYTHIC (Dark Purple) - For eternal affinity items (Time, Space, Life)</li>
 * </ul>
 *
 * <p>Visual representation:</p>
 * Item names will be colored according to their rarity when displayed in
 * tooltips, creative inventory, and chat messages.
 */
public class ModRarities {
    /**
     * LEGENDARY rarity tier for advanced deviant affinity items.
     * Displayed in gold color to indicate powerful, rare items.
     *
     * <p>Used for:</p>
     * <ul>
     *   <li>Lightning affinity stone</li>
     *   <li>Ice affinity stone</li>
     *   <li>Sound affinity stone</li>
     *   <li>Gravity affinity stone</li>
     * </ul>
     */
    public static final EnumProxy<Rarity> LEGENDARY = new EnumProxy<>(
            Rarity.class,
            -1, // Ordinal (automatically assigned)
            "elementalrealms:legendary", // Internal identifier
            (UnaryOperator<Style>) style -> style.withColor(ChatFormatting.GOLD) // Gold text color
    );

    /**
     * MYTHIC rarity tier for ultimate eternal affinity items.
     * Displayed in dark purple color to indicate extremely rare, unique items.
     *
     * <p>Used for:</p>
     * <ul>
     *   <li>Time affinity stone</li>
     *   <li>Space affinity stone</li>
     *   <li>Life affinity stone</li>
     * </ul>
     *
     * <p>Note:</p>
     * Players can only have one ETERNAL affinity at a time, making these
     * the rarest and most exclusive items in the mod.
     */
    public static final EnumProxy<Rarity> MYTHIC = new EnumProxy<>(
            Rarity.class,
            -1, // Ordinal (automatically assigned)
            "elementalrealms:mythic", // Internal identifier
            (UnaryOperator<Style>) style -> style.withColor(ChatFormatting.DARK_PURPLE) // Dark purple text color
    );

}
