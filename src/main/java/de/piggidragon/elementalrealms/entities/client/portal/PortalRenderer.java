package de.piggidragon.elementalrealms.entities.client.portal;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.entities.custom.PortalEntity;
import de.piggidragon.elementalrealms.entities.variants.PortalVariant;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;

import java.util.Map;

/**
 * Renders portal entities with translucent effects and animations.
 */
public class PortalRenderer extends EntityRenderer<PortalEntity, PortalRenderState> {

    /**
     * Maps portal variants to their corresponding texture locations.
     * Kept as a static map for fast lookup during render state extraction.
     */
    private static final Map<PortalVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(PortalVariant.class), map -> {
                map.put(PortalVariant.SCHOOL, ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "textures/entity/portal/portal_entity_school.png"));
                map.put(PortalVariant.ELEMENTAL, ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "textures/entity/portal/portal_entity_elemental.png"));
                map.put(PortalVariant.DEVIANT, ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "textures/entity/portal/portal_entity_deviant.png"));
                map.put(PortalVariant.ETERNAL, ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "textures/entity/portal/portal_entity_eternal.png"));
            });

    /**
     * The 3D model used for rendering portals.
     * Initialized in the renderer constructor by baking the model layer.
     */
    private final PortalModel<PortalEntity> model;

    /**
     * Constructs a PortalRenderer and prepares the model layer.
     *
     * @param context provider context containing model baking utilities and render settings
     */
    public PortalRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new PortalModel<>(context.bakeLayer(PortalModel.LAYER_LOCATION));
    }

    /**
     * Create an empty render state container for this renderer.
     *
     * @return a fresh PortalRenderState used to transfer entity data to the render pass
     */
    @Override
    public PortalRenderState createRenderState() {
        return new PortalRenderState();
    }

    /**
     * Populate the supplied render state with data from the entity.
     * Copies animation states and picks the correct texture according to the entity variant.
     *
     * @param entity the portal entity to read data from
     * @param reusedState the render state instance to populate (reused between frames)
     * @param partialTick interpolation value for smooth animations (not directly used here)
     */
    @Override
    public void extractRenderState(PortalEntity entity, PortalRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.spawnAnimationState.copyFrom(entity.spawnAnimationState);
        reusedState.idleAnimationState.copyFrom(entity.idleAnimationState);
        reusedState.yRot = entity.getYRot();
        reusedState.packedLight = this.getPackedLightCoords(entity, partialTick);

        // Select texture by variant; fall back to SCHOOL if mapping missing.
        reusedState.texture = LOCATION_BY_VARIANT.get(entity.getVariant());
        if (reusedState.texture == null) {
            reusedState.texture = LOCATION_BY_VARIANT.get(PortalVariant.SCHOOL);
        }
    }

    /**
     * Submit the prepared render state to the rendering pipeline.
     * Handles pose transforms, model animation setup and node submission.
     *
     * @param renderState the prepared render state containing animation and texture info
     * @param poseStack current transformation stack for positioning and rotation
     * @param nodeCollector collector used to submit the model for rendering
     * @param cameraRenderState information about the camera view and clipping
     */
    @Override
    public void submit(PortalRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();

        // position and face the entity towards the camera
        poseStack.translate(0.0D, 0.0D, 0.0D);
        poseStack.mulPose(Axis.YP.rotationDegrees(-renderState.yRot + 180.0F));

        ResourceLocation texture = renderState.texture;

        // Setup model pose and animations based on the render state
        model.setupAnim(renderState);

        nodeCollector.submitModel(
                model,
                renderState,
                poseStack,
                RenderType.entityTranslucent(texture),
                renderState.packedLight,
                OverlayTexture.NO_OVERLAY,
                ARGB.color(255, 255, 255, 255),
                null,
                0,
                null
        );

        poseStack.popPose();
        super.submit(renderState, poseStack, nodeCollector, cameraRenderState);
    }
}
