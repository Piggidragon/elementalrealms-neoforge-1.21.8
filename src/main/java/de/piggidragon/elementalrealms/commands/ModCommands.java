package de.piggidragon.elementalrealms.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.magic.affinities.Affinity;
import de.piggidragon.elementalrealms.magic.affinities.ModAffinities;
import de.piggidragon.elementalrealms.magic.affinities.ModAffinitiesRoll;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = ElementalRealms.MODID)
public class ModCommands {


    public static final SuggestionProvider<CommandSourceStack> AFFINITY_SUGGESTIONS = (context, builder) -> {
        for (Affinity a : Affinity.values()) {
            builder.suggest(a.toString());
        }
        return builder.buildFuture();
    };

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        var dispatcher = event.getDispatcher();
        ElementalRealms.LOGGER.info("COMMANDREGISTER");
        // /affinities list
        dispatcher.register(Commands.literal("affinities")
                .then(Commands.literal("list")
                        .executes(ctx -> {
                            ServerPlayer player = ctx.getSource().getPlayerOrException();
                            var affinities = ModAffinities.getAffinities(player);
                            ctx.getSource().sendSuccess(() -> Component.literal("Your affinities: " + affinities), false);
                            return 1;
                        })
                )
                // /affinities set <affinity>
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
                // /affinities clear
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
                // /affinities random <chance>
                .then(Commands.literal("reroll")
                        .executes(ctx -> {
                            ServerPlayer player = ctx.getSource().getPlayerOrException();
                            try {
                                ModAffinities.clearAffinities(player);
                            } catch (Exception ignored) {
                            }
                            for (Affinity affinity : ModAffinitiesRoll.rollAffinities(player)) {
                                if (affinity != Affinity.NONE) {
                                    try {
                                        ModAffinities.addAffinity(player, affinity);
                                    } catch (Exception e) {
                                        // Should not happen, as we roll only affinities the player does not have yet
                                        ElementalRealms.LOGGER.error("Error while re-rolling affinities for player " + player.getName().getString() + ": " + e.getMessage());
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
