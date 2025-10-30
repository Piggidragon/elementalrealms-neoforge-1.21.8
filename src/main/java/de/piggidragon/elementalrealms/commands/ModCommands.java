package de.piggidragon.elementalrealms.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.magic.affinities.Affinity;
import de.piggidragon.elementalrealms.magic.affinities.ModAffinities;
import de.piggidragon.elementalrealms.magic.affinities.ModAffinitiesRoll;
import de.piggidragon.elementalrealms.portals.PortalUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

/**
 * Registers /affinities command for managing player affinities.
 * Subcommands: list, set, clear, reroll
 */
@EventBusSubscriber(modid = ElementalRealms.MODID)
public class ModCommands {

    /**
     * Provides auto-completion suggestions for valid affinity names
     */
    public static final SuggestionProvider<CommandSourceStack> AFFINITY_SUGGESTIONS = (context, builder) -> {
        for (Affinity a : Affinity.values()) {
            builder.suggest(a.toString());
        }
        return builder.buildFuture();
    };

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        var dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("portal")
                .requires(cs -> cs.hasPermission(2))

                .then(Commands.literal("find")
                        .executes(ctx -> {
                            ServerPlayer player = ctx.getSource().getPlayerOrException();
                            var portal = PortalUtils.findNearestPortal(player.level(), player.position(), 300);
                            ctx.getSource().sendSuccess(() -> Component.literal("Nearest Portal: " + portal.getPositionVec()), false);
                            return 1;
                        })
                )
        );

        dispatcher.register(Commands.literal("affinities")
                .requires(cs -> cs.hasPermission(2)) // Requires OP level 2

                // List current affinities
                .then(Commands.literal("list")
                        .executes(ctx -> {
                            ServerPlayer player = ctx.getSource().getPlayerOrException();
                            var affinities = ModAffinities.getAffinities(player);
                            ctx.getSource().sendSuccess(() -> Component.literal("Your affinities: " + affinities), false);
                            return 1;
                        })
                )

                // Manually set a specific affinity
                .then(Commands.literal("set")
                        .then(Commands.argument("affinity", StringArgumentType.word())
                                .suggests(AFFINITY_SUGGESTIONS)
                                .executes(ctx -> {
                                    ServerPlayer player = ctx.getSource().getPlayerOrException();
                                    String affinityName = StringArgumentType.getString(ctx, "affinity");
                                    try {
                                        Affinity affinity = Affinity.valueOf(affinityName.toUpperCase());
                                        ModAffinities.addAffinity(player, affinity);
                                        ctx.getSource().sendSuccess(() -> Component.literal("Set affinity: " + affinity), false);
                                    } catch (IllegalArgumentException e) {
                                        ctx.getSource().sendFailure(Component.literal("Invalid affinity: " + affinityName));
                                    } catch (Exception e) {
                                        ctx.getSource().sendFailure(Component.literal(e.getMessage()));
                                    }
                                    return 1;
                                })
                        )
                )

                // Clear all player affinities
                .then(Commands.literal("clear")
                        .executes(ctx -> {
                            ServerPlayer player = ctx.getSource().getPlayerOrException();
                            try {
                                ModAffinities.clearAffinities(player);
                            } catch (Exception e) {
                                ctx.getSource().sendFailure(Component.literal("No affinities to clear!"));
                                return 0;
                            }
                            ctx.getSource().sendSuccess(() -> Component.literal("Cleared all affinities."), false);
                            return 1;
                        })
                )

                // Re-roll affinities randomly
                .then(Commands.literal("reroll")
                        .executes(ctx -> {
                            ServerPlayer player = ctx.getSource().getPlayerOrException();
                            // Clear existing affinities
                            try {
                                ModAffinities.clearAffinities(player);
                            } catch (Exception ignored) {
                            }
                            // Roll and apply new random affinities
                            for (Affinity affinity : ModAffinitiesRoll.rollAffinities(player)) {
                                if (affinity != Affinity.VOID) {
                                    try {
                                        ModAffinities.addAffinity(player, affinity);
                                    } catch (Exception e) {
                                        // Should not occur since we're rolling unique affinities
                                        ElementalRealms.LOGGER.error("Error re-rolling affinities for player " + player.getName().getString() + ": " + e.getMessage());
                                    }
                                }
                            }
                            ctx.getSource().sendSuccess(() -> Component.literal("Re-rolled affinities."), false);
                            return 1;
                        })
                )
        );

    }
}
