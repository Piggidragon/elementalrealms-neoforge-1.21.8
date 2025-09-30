package de.piggidragon.elementalrealms;

import com.mojang.logging.LogUtils;
import de.piggidragon.elementalrealms.attachments.ModAttachments;
import de.piggidragon.elementalrealms.commands.ModCommands;
import de.piggidragon.elementalrealms.magic.affinities.ModAffinities;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(ElementalRealms.MODID)
public class ElementalRealms {
    public static final String MODID = "elementalrealms";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ElementalRealms(IEventBus modEventBus, ModContainer modContainer) {
        ModAttachments.register(modEventBus);
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("Common setup for {}", MODID);
    }
}