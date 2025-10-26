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
 * Renders portal entities with translucent effects and animations.
 */
public class PortalRenderer extends EntityRenderer<PortalEntity, PortalRenderState> {

    /**
     * Maps portal variants to their corresponding texture locations.
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
     */
    private final PortalModel<PortalEntity> model;

    public PortalRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new PortalModel<>(context.bakeLayer(PortalModel.LAYER_LOCATION));
    }

    /**
     * Creates a new render state instance for this renderer.
     *
     * @return A new PortalRenderState instance
     */
    @Override
    public PortalRenderState createRenderState() {
        return new PortalRenderState();
    }

    /**
     * Extracts rendering information from the entity and stores it in the render state.
     * Synchronizes animation states and determines which texture to use based on variant.
     *
     * @param entity The portal entity to extract data from
     * @param reusedState The render state to populate
     * @param partialTick Frame interpolation value
     */
    @Override
    public void extractRenderState(PortalEntity entity, PortalRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.spawnAnimationState.copyFrom(entity.spawnAnimationState);
        reusedState.idleAnimationState.copyFrom(entity.idleAnimationState);
        reusedState.yRot = entity.getYRot();

        reusedState.texture = LOCATION_BY_VARIANT.get(entity.getVariant());
        if (reusedState.texture == null) {
            reusedState.texture = LOCATION_BY_VARIANT.get(PortalVariant.SCHOOL);
        }
    }

    /**
     * Renders the portal entity using its model and texture.
     * Applies translucency effect and rotation based on entity's yaw.
     *
     * @param renderState The render state containing rendering data
     * @param poseStack The transformation matrix stack
     * @param buffer The buffer for rendering geometry
     * @param packedLight The combined light level
     */
    @Override
    public void render(PortalRenderState renderState, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        poseStack.translate(0.0D, 0.0D, 0.0D);
        poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(-renderState.yRot + 180.0F));

        ResourceLocation texture = renderState.texture;

        model.setupAnim(renderState);
        model.renderToBuffer(
                poseStack,
                buffer.getBuffer(RenderType.entityTranslucent(texture)),
                packedLight,
                OverlayTexture.NO_OVERLAY
        );

        poseStack.popPose();
        super.render(renderState, poseStack, buffer, packedLight);
    }
}
