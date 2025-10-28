package de.piggidragon.elementalrealms.entities.client.portal;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;

/**
 * Container for per-frame rendering data for a portal entity.
 * This object is populated in extractRenderState and consumed during submit.
 */
public class PortalRenderState extends EntityRenderState {
    /**
     * Animation state for the portal's idle floating animation.
     * Use copyFrom to synchronize with the entity's animation state.
     */
    public final AnimationState idleAnimationState = new AnimationState();

    /**
     * Animation state for the portal's spawn/appearance animation.
     * Use copyFrom to synchronize with the entity's animation state.
     */
    public final AnimationState spawnAnimationState = new AnimationState();

    /**
     * The portal's Y-axis rotation in degrees.
     * Used to orient the model towards the camera.
     */
    public float yRot;

    /**
     * The texture location to use for rendering this portal.
     */
    public ResourceLocation texture;

    /**
     * Packed light value used by the renderer to determine lighting for the model.
     * Typically computed from world light at the entity position.
     */
    public int packedLight;

    /**
     * Default constructor. Instances are typically created by the renderer via createRenderState().
     */
    public PortalRenderState() {
    }
}
