package de.piggidragon.elementalrealms.items.magic.dimension;

import de.piggidragon.elementalrealms.entities.ModEntities;
import de.piggidragon.elementalrealms.entities.custom.PortalEntity;
import de.piggidragon.elementalrealms.level.ModLevel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Consumer;

public class SchoolStaff extends Item {
    public SchoolStaff(Properties properties) {
        super(properties);
    }

    private static void spawnPortal(Level level, Player player) {
        PortalEntity portal = new PortalEntity(ModEntities.PORTAL_ENTITY.get(), level, true, 200, player.getServer().getLevel(ModLevel.SCHOOL_DIMENSION), player.getUUID());

        Vec3 lookVec = player.getLookAngle();
        double distance = 2.0;
        double x = player.getX() + lookVec.x * distance;
        double y = player.getY() + 0.5;
        double z = player.getZ() + lookVec.z * distance;

        portal.setPos(x, y, z);
        portal.setYRot(player.getYRot());
        level.addFreshEntity(portal);
    }

    private static void removeOldPortals(Level level, Player player) {
        List<PortalEntity> portals = level.getEntitiesOfClass(
                PortalEntity.class,
                player.getBoundingBox().inflate(1000),
                portal -> portal.getOwnerUUID() != null && portal.getOwnerUUID().equals(player.getUUID())
        );

        for (PortalEntity portal : portals) {
            portal.discard();
        }
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (level.dimension() != Level.OVERWORLD && level.dimension() != Level.NETHER && level.dimension() != Level.END) {
            player.displayClientMessage(Component.literal("Can't use this here..."), true);
            return InteractionResult.PASS;
        }
        if (!level.isClientSide()) {

            removeOldPortals(level, player);
            spawnPortal(level, player);

            player.getMainHandItem().hurtAndBreak(1, ((ServerLevel) level), player,
                    item -> player.onEquippedItemBroken(item, EquipmentSlot.MAINHAND));

            player.getCooldowns().addCooldown(player.getMainHandItem(), 0);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        if (Screen.hasShiftDown()) {
            tooltipAdder.accept(Component.translatable("itemtooltip.elementalrealms.dimension_staff.line1"));
            tooltipAdder.accept(Component.translatable("itemtooltip.elementalrealms.dimension_staff.line2"));
        } else {
            tooltipAdder.accept(Component.translatable("itemtooltip.elementalrealms.shift"));
        }
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
    }
}
