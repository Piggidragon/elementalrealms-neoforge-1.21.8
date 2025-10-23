package de.piggidragon.elementalrealms.entities.client.portal;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.AnimationState;

/**
 * Stores rendering data for portal entities between frames.
 * Render states are reusable objects that hold all necessary information for rendering
 * without directly referencing the entity, improving performance and reducing coupling.
 *
 * <p>Contains:</p>
 * <ul>
 *   <li>Animation states for smooth visual transitions</li>
 *   <li>Rotation data for proper orientation</li>
 * </ul>
 */
public class PortalRenderState extends EntityRenderState {
    /**
     * Animation state for the portal's idle/floating animation
     */
    public final AnimationState idleAnimationState = new AnimationState();

    /**
     * Animation state for the portal's spawn/appearance animation
     */
    public final AnimationState spawnAnimationState = new AnimationState();

    /**
     * Y-axis rotation in degrees for orienting the portal model
     */
    public float yRot;
}
