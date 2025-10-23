package de.piggidragon.elementalrealms.entities.client.portal;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.entities.custom.PortalEntity;
import de.piggidragon.elementalrealms.entities.variants.PortalVariant;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

/**
 * Client-side renderer for portal entities. Handles visual rendering of dimensional portals
 * including model display, texture mapping, animations, and transparency effects.
 *
 * <p>Rendering features:</p>
 * <ul>
 *   <li>Translucent portal effect using entity translucent render type</li>
 *   <li>Dynamic texture selection based on portal variant</li>
 *   <li>Rotation handling to face the player</li>
 *   <li>Animation state synchronization from entity</li>
 * </ul>
 */
public class PortalRenderer extends EntityRenderer<PortalEntity, PortalRenderState> {

    /**
     * Maps portal variants to their corresponding texture resources.
     * Currently only contains the School dimension portal texture.
     */
    private static final Map<PortalVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(PortalVariant.class), map -> {
                map.put(PortalVariant.SCHOOL,
                        ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "textures/entity/portal/portal_entity_school.png"));
            });

    /** The 3D model used to render the portal entity */
    private final PortalModel<PortalEntity> model;

    /**
     * Constructs the portal renderer with necessary rendering context.
     * Initializes the model by baking the layer definition from the model class.
     *
     * @param context Entity renderer provider context containing model baking capabilities
     */
    public PortalRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new PortalModel<>(context.bakeLayer(PortalModel.LAYER_LOCATION));
    }

    /**
     * Creates a new render state instance for storing rendering information.
     * Render states are reused between frames to reduce garbage collection overhead.
     *
     * @return New portal render state instance
     */
    @Override
    public PortalRenderState createRenderState() {
        return new PortalRenderState();
    }

    /**
     * Extracts rendering information from the portal entity into the render state.
     * Called every frame to update the render state with current entity data.
     *
     * @param entity The portal entity being rendered
     * @param reusedState The render state to populate with current data
     * @param partialTick Fraction of a tick for smooth interpolation (0.0 to 1.0)
     */
    @Override
    public void extractRenderState(PortalEntity entity, PortalRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        // Copy animation states from entity to render state
        reusedState.spawnAnimationState.copyFrom(entity.spawnAnimationState);
        reusedState.idleAnimationState.copyFrom(entity.idleAnimationState);
        // Store rotation for proper model orientation
        reusedState.yRot = entity.getYRot();
    }

    /**
     * Performs the actual rendering of the portal entity using the render state.
     * Applies transformations, selects texture, and renders the model with transparency.
     *
     * @param renderState Current render state containing animation and position data
     * @param poseStack Matrix stack for applying transformations (translation, rotation, scale)
     * @param buffer Multi-buffer source for obtaining render buffers
     * @param packedLight Light level at the entity's position (block + sky light)
     */
    @Override
    public void render(PortalRenderState renderState, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        // Position portal at origin (entity position already applied by renderer)
        poseStack.translate(0.0D, 0.0D, 0.0D);

        // Rotate portal to face away from viewer (180° offset)
        poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(-renderState.yRot + 180.0F));

        // Select texture based on portal variant (currently only SCHOOL variant exists)
        ResourceLocation texture = LOCATION_BY_VARIANT.get(PortalVariant.SCHOOL);

        // Update model animations based on current render state
        model.setupAnim(renderState);

        // Render the model with translucent effect (allows seeing through portal)
        model.renderToBuffer(
                poseStack,
                buffer.getBuffer(RenderType.entityTranslucent(texture)),
                packedLight,
                OverlayTexture.NO_OVERLAY // No damage overlay effect
        );

        poseStack.popPose();
        super.render(renderState, poseStack, buffer, packedLight);
    }
}
