package de.piggidragon.elementalrealms.entities.client.portal;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;

/**
 * Container for per-frame rendering data for a portal entity.
 * This object is populated in extractRenderState and consumed during submit.
 */
public class PortalRenderState extends EntityRenderState {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState spawnAnimationState = new AnimationState();
    public ResourceLocation textureLocation;
    public int packedLight;

    public float yRot;

    public PortalRenderState() {}
}
