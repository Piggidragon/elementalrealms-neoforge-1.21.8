package de.piggidragon.elementalrealms.items.dimension;

import de.piggidragon.elementalrealms.entities.EntityTypes;
import de.piggidragon.elementalrealms.entities.PortalEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class DimensionStaff extends Item {
    public DimensionStaff(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            PortalEntity entity = new PortalEntity(EntityTypes.PORTAL_ENTITY.get(), level);

            Vec3 lookVec = player.getLookAngle();
            double distance = 2.0;
            double x = player.getX() + lookVec.x * distance;
            double y = player.getY() + lookVec.y * distance;
            double z = player.getZ() + lookVec.z * distance;

            entity.setPos(x, y, z);
            entity.setYRot(player.getYRot());
            level.addFreshEntity(entity);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
