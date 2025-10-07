package de.piggidragon.elementalrealms.dimensions.beginner;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Collections;
import java.util.Set;

public class SchoolDimensionPortal extends Block {

    private static final ResourceKey<Level> SCHOOL_DIMENSION = ResourceKey.create(
            Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath("elementalrealms", "school"));

    private static final ResourceKey<Level> OVERWORLD = Level.OVERWORLD;

    private static final double SCHOOL_X = 100;
    private static final double SCHOOL_Y = 50;
    private static final double SCHOOL_Z = 100;

    private static final double OVERWORLD_X = 0;
    private static final double OVERWORLD_Y = 100;
    private static final double OVERWORLD_Z = 0;

    public SchoolDimensionPortal(Properties properties) {
        super(properties);
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier) {
        if (!level.isClientSide && entity instanceof ServerPlayer player) {
            if (player.isOnPortalCooldown()) {
                super.entityInside(state, level, pos, entity, effectApplier);
                return;
            }

            // Keine relativen Bewegungen (absolute Position/Yaw/Pitch setzen)
            Set<Relative> relatives = Collections.emptySet();
            float yaw = player.getYRot();
            float pitch = player.getXRot();
            boolean setCamera = true; // oder false, falls Kamera nicht angepasst werden soll

            if (player.level().dimension() == SCHOOL_DIMENSION) {
                ServerLevel overworld = player.getServer().getLevel(OVERWORLD);
                if (overworld != null) {
                    player.setPortalCooldown();
                    player.teleportTo(overworld, OVERWORLD_X, OVERWORLD_Y, OVERWORLD_Z, relatives, yaw, pitch, setCamera);
                }
            } else {
                ServerLevel school = player.getServer().getLevel(SCHOOL_DIMENSION);
                if (school != null) {
                    player.setPortalCooldown();
                    player.teleportTo(school, SCHOOL_X, SCHOOL_Y, SCHOOL_Z, relatives, yaw, pitch, setCamera);
                }
            }
        }
        super.entityInside(state, level, pos, entity, effectApplier);
    }
}