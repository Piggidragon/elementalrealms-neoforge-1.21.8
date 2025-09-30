package de.piggidragon.elementalrealms.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.magic.affinities.Affinity;
import de.piggidragon.elementalrealms.magic.affinities.ModAffinities;
import de.piggidragon.elementalrealms.magic.affinities.ModAffinitiesRoll;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

public class ModCommands {
    public static void initializeCommands() {
        ElementalRealms.LOGGER.info("Initializing Mod Commands for " + ElementalRealms.MODID);
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        var dispatcher = event.getDispatcher();

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
                                .executes(ctx -> {
                                    ServerPlayer player = ctx.getSource().getPlayerOrException();
                                    String affinityName = StringArgumentType.getString(ctx, "affinity");
                                    try {
                                        Affinity affinity = Affinity.valueOf(affinityName.toUpperCase());
                                        ModAffinities.addAffinity(player, affinity);
                                        ctx.getSource().sendSuccess(() -> Component.literal("Set affinity: " + affinity), false);
                                    } catch (IllegalArgumentException e) {
                                        ctx.getSource().sendFailure(Component.literal("Invalid affinity!"));
                                    }
                                    return 1;
                                })
                        )
                )
                // /affinities random <chance>
                .then(Commands.literal("reroll")
                        .executes(ctx -> {
                            ServerPlayer player = ctx.getSource().getPlayerOrException();
                            for (Affinity affinity : ModAffinitiesRoll.rollAffinities(player)) {
                                if (affinity != Affinity.NONE) {
                                    ModAffinities.addAffinity(player, affinity);
                                }
                            }
                            return 1;
                        })
                )
        );

    }
}
