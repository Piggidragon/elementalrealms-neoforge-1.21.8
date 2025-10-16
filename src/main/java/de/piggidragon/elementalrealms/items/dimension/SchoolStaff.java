package de.piggidragon.elementalrealms.items.dimension;

import de.piggidragon.elementalrealms.entities.ModEntities;
import de.piggidragon.elementalrealms.entities.custom.PortalEntity;
import de.piggidragon.elementalrealms.level.ModLevel;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SchoolStaff extends Item {
    public SchoolStaff(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (level.dimension() != Level.OVERWORLD) {
            return InteractionResult.PASS;
        }
        if (!level.isClientSide()) {
            PortalEntity portal = new PortalEntity(ModEntities.PORTAL_ENTITY.get(), level);
            portal.setTargetLevel(player.getServer().getLevel(ModLevel.SCHOOL_DIMENSION));

            Vec3 lookVec = player.getLookAngle();
            double distance = 2.0;
            double x = player.getX() + lookVec.x * distance;
            double y = player.getY() + 0.3;
            double z = player.getZ() + lookVec.z * distance;

            portal.setPos(x, y, z);
            portal.setYRot(player.getYRot());
            level.addFreshEntity(portal);

            portal.setDespawnTimer(portal, 200);
            player.getMainHandItem().hurtAndBreak(1, ((ServerLevel) level), player,
                    item -> player.onEquippedItemBroken(item, EquipmentSlot.MAINHAND));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
