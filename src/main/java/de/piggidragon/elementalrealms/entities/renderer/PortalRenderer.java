package de.piggidragon.elementalrealms.entities.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import de.piggidragon.elementalrealms.entities.PortalEntity;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class PortalRenderer extends EntityRenderer<PortalEntity, EntityRenderState> {

    private final ModelPart modelPart;

    public PortalRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.modelPart = context.bakeLayer(ModelLayers.STANDING_BANNER);
    }

    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }

    @Override
    public void render(EntityRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        modelPart.render(
                poseStack,
                bufferSource.getBuffer(RenderType.entitySolid(
                        ResourceLocation.fromNamespaceAndPath("minecraft", "textures/block/stone.png"))),
                packedLight,
                OverlayTexture.NO_OVERLAY
        );
        poseStack.popPose();
    }
}
