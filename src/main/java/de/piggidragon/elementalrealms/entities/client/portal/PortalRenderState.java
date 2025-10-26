package de.piggidragon.elementalrealms.entities.client.portal;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;

/**
 * Stores rendering data for portal entities between frames.
 */
public class PortalRenderState extends EntityRenderState {
    /**
     * Animation state for the portal's idle floating animation.
     */
    public final AnimationState idleAnimationState = new AnimationState();

    /**
     * Animation state for the portal's spawn/appearance animation.
     */
    public final AnimationState spawnAnimationState = new AnimationState();

    /**
     * The portal's Y-axis rotation in degrees.
     */
    public float yRot;

    /**
     * The texture location to use for rendering this portal.
     */
    public ResourceLocation texture;

    public PortalRenderState() {
    }
}
