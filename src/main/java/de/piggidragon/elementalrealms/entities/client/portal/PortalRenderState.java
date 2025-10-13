package de.piggidragon.elementalrealms.entities.client.portal;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class PortalRenderState extends EntityRenderState {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState spawnAnimationState = new AnimationState();
    public float yRot;
}
