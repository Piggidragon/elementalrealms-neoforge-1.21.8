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

public class PortalRenderer extends EntityRenderer<PortalEntity, PortalRenderState> {

    private static final Map<PortalVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(PortalVariant.class), map -> {
                map.put(PortalVariant.SCHOOL,
                        ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "textures/entity/portal/portal_entity_school.png"));
            });

    private final PortalModel<PortalEntity> model;

    public PortalRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new PortalModel<>(context.bakeLayer(PortalModel.LAYER_LOCATION));
    }

    @Override
    public PortalRenderState createRenderState() {
        return new PortalRenderState();
    }

    @Override
    public void extractRenderState(PortalEntity entity, PortalRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.spawnAnimationState.copyFrom(entity.spawnAnimationState);
        reusedState.idleAnimationState.copyFrom(entity.idleAnimationState);
        reusedState.yRot = entity.getYRot();
    }

    @Override
    public void render(PortalRenderState renderState, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        poseStack.translate(0.0D, 0.0D, 0.0D);
        poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(-renderState.yRot + 180.0F));

        ResourceLocation texture = LOCATION_BY_VARIANT.get(PortalVariant.SCHOOL);

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
