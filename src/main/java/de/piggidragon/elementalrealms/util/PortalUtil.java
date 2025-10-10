package de.piggidragon.elementalrealms.util;

import de.piggidragon.elementalrealms.attachments.ModAttachments;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;

public class PortalUtil {

    public static BlockPos safeTargetBlock(ServerLevel level, BlockPos target, int radius, ServerPlayer player) {
        if (isSafeTeleport(level, target, player)) return target;

        for (int r = 1; r <= radius; r++) {
            for (int dx = -r; dx <= r; dx++) {
                for (int dz = -r; dz <= r; dz++) {
                    BlockPos pos = target.offset(dx, 0, dz);
                    if (isSafeTeleport(level, pos, player)) return pos;
                }
            }
        }

        return target;
    }

    private static boolean isSafeTeleport(ServerLevel level, BlockPos pos, ServerPlayer player) {
        for (int dy = 0; dy <= 1; dy++) {
            BlockPos check = pos.above(dy);
            BlockState state = level.getBlockState(check);

            if (!state.isAir() && !state.getCollisionShape(level, check).isEmpty()) {
                return false;
            }
            if (player.getData(ModAttachments.OVERWORLD_RETURN_POS) != null){
                BlockPos returnPos = player.getData(ModAttachments.OVERWORLD_RETURN_POS);
                if (pos.getX() == returnPos.getX() && pos.getZ() == returnPos.getZ()) {
                    return false;
                }
            }

        }
        return true;
    }
}
