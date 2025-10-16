package de.piggidragon.elementalrealms.entities.client.portal;

import de.piggidragon.elementalrealms.ElementalRealms;
import de.piggidragon.elementalrealms.entities.custom.PortalEntity;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class PortalModel<T extends PortalEntity> extends EntityModel<PortalRenderState> {

    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(ElementalRealms.MODID, "portal_entity"), "main");

    private final ModelPart Portal;

    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation spawnAnimation;

    public PortalModel(ModelPart root) {
        super(root);
        this.Portal = root.getChild("Portal");

        this.idleAnimation = PortalAnimations.ANIMATION_IDLE_HOVER.bake(root);
        this.spawnAnimation = PortalAnimations.ANIMATION_SPAWN.bake(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Portal = partdefinition.addOrReplaceChild("Portal", CubeListBuilder.create(), PartPose.offset(-1.9167F, 17.5F, 0.0F));

        PartDefinition base2 = Portal.addOrReplaceChild("base2", CubeListBuilder.create().texOffs(0, 36).addBox(16.6915F, -15.0F, -2.0F, 3.0F, 26.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(36, 0).addBox(-1.5F, -15.0F, -2.0F, 3.0F, 26.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(14, 36).addBox(5.0957F, 14.5957F, -2.0F, 8.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(38, 30).addBox(5.0957F, -21.5793F, -2.0F, 8.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.5833F, 2.8333F, 0.0F));

        PartDefinition kurve_unten_link = Portal.addOrReplaceChild("kurve_unten_link", CubeListBuilder.create(), PartPose.offsetAndRotation(8.9167F, 16.8333F, 0.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition group_90_11 = kurve_unten_link.addOrReplaceChild("90_11", CubeListBuilder.create(), PartPose.offset(3.0716F, -12.6812F, 0.0F));

        PartDefinition cube_r1 = group_90_11.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(50, 5).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.008F)), PartPose.offsetAndRotation(-2.502F, 12.3172F, 0.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition cube_r2 = group_90_11.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(50, 0).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.007F)), PartPose.offsetAndRotation(-2.7877F, 11.9449F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r3 = group_90_11.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(14, 48).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.006F)), PartPose.offsetAndRotation(-3.16F, 11.6591F, 0.0F, 0.0F, 0.0F, -1.0472F));

        PartDefinition cube_r4 = group_90_11.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(42, 47).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.005F)), PartPose.offsetAndRotation(-3.5936F, 11.4795F, 0.0F, 0.0F, 0.0F, -1.309F));

        PartDefinition cube_r5 = group_90_11.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(28, 47).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.004F)), PartPose.offsetAndRotation(-4.2684F, 11.2F, 0.0F, 0.0F, 0.0F, -1.0472F));

        PartDefinition cube_r6 = group_90_11.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(14, 43).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.003F)), PartPose.offsetAndRotation(-4.8479F, 10.7554F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r7 = group_90_11.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(38, 42).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.002F)), PartPose.offsetAndRotation(-5.2925F, 10.1759F, 0.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition cube_r8 = group_90_11.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(38, 37).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(-5.572F, 9.5011F, 0.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition group_90_12 = kurve_unten_link.addOrReplaceChild("group_90_12", CubeListBuilder.create(), PartPose.offset(6.0716F, -8.6812F, 0.0F));

        PartDefinition cube_r9 = group_90_12.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(42, 52).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(-3.2958F, 10.7774F, 0.0F, 0.0F, 0.0F, -1.309F));

        PartDefinition cube_r10 = group_90_12.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(52, 37).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.002F)), PartPose.offsetAndRotation(-3.9706F, 10.4979F, 0.0F, 0.0F, 0.0F, -1.0472F));

        PartDefinition cube_r11 = group_90_12.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(28, 52).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.003F)), PartPose.offsetAndRotation(-4.55F, 10.0533F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r12 = group_90_12.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(50, 25).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.004F)), PartPose.offsetAndRotation(-4.9947F, 9.4738F, 0.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition cube_r13 = group_90_12.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(50, 20).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.005F)), PartPose.offsetAndRotation(-5.7395F, 7.9931F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r14 = group_90_12.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(50, 15).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.006F)), PartPose.offsetAndRotation(-5.4538F, 8.3654F, 0.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition cube_r15 = group_90_12.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(50, 10).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.007F)), PartPose.offsetAndRotation(-5.2742F, 8.799F, 0.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition kurve_unten_rechts = Portal.addOrReplaceChild("kurve_unten_rechts", CubeListBuilder.create(), PartPose.offset(-6.0833F, 16.8333F, 0.0F));

        PartDefinition group_90_2 = kurve_unten_rechts.addOrReplaceChild("90_2", CubeListBuilder.create(), PartPose.offset(3.0716F, -11.6812F, 0.0F));

        PartDefinition cube_r16 = group_90_2.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(70, 75).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.008F)), PartPose.offsetAndRotation(-1.9062F, 11.7215F, 0.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition cube_r17 = group_90_2.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(14, 73).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.007F)), PartPose.offsetAndRotation(-2.1919F, 11.3491F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r18 = group_90_2.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(56, 72).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.006F)), PartPose.offsetAndRotation(-2.5643F, 11.0634F, 0.0F, 0.0F, 0.0F, -1.0472F));

        PartDefinition cube_r19 = group_90_2.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(42, 72).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.005F)), PartPose.offsetAndRotation(-2.9979F, 10.8838F, 0.0F, 0.0F, 0.0F, -1.309F));

        PartDefinition cube_r20 = group_90_2.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(28, 72).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.004F)), PartPose.offsetAndRotation(-3.6727F, 10.6043F, 0.0F, 0.0F, 0.0F, -1.0472F));

        PartDefinition cube_r21 = group_90_2.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(0, 71).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.003F)), PartPose.offsetAndRotation(-4.2521F, 10.1596F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r22 = group_90_2.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(70, 70).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.002F)), PartPose.offsetAndRotation(-4.6968F, 9.5802F, 0.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition cube_r23 = group_90_2.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(70, 65).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(-4.9763F, 8.9054F, 0.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition group_90_3 = kurve_unten_rechts.addOrReplaceChild("90_3", CubeListBuilder.create(), PartPose.offset(6.0716F, -7.6812F, 0.0F));

        PartDefinition cube_r24 = group_90_3.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(78, 5).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(-2.7F, 10.1817F, 0.0F, 0.0F, 0.0F, -1.309F));

        PartDefinition cube_r25 = group_90_3.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(78, 0).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.002F)), PartPose.offsetAndRotation(-3.3748F, 9.9022F, 0.0F, 0.0F, 0.0F, -1.0472F));

        PartDefinition cube_r26 = group_90_3.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(56, 77).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.003F)), PartPose.offsetAndRotation(-3.9543F, 9.4575F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r27 = group_90_3.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(42, 77).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.004F)), PartPose.offsetAndRotation(-4.3989F, 8.8781F, 0.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition cube_r28 = group_90_3.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(28, 77).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.005F)), PartPose.offsetAndRotation(-5.1437F, 7.3973F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r29 = group_90_3.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(76, 30).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.006F)), PartPose.offsetAndRotation(-4.858F, 7.7697F, 0.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition cube_r30 = group_90_3.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(0, 76).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-4.6784F, 8.2033F, 0.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition kurve_oben_rechts = Portal.addOrReplaceChild("kurve_oben_rechts", CubeListBuilder.create(), PartPose.offsetAndRotation(-5.0833F, -17.1667F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition group_90_13 = kurve_oben_rechts.addOrReplaceChild("90_13", CubeListBuilder.create(), PartPose.offset(-2.9284F, 31.3188F, 0.0F));

        PartDefinition cube_r31 = group_90_13.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(14, 58).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.008F)), PartPose.offsetAndRotation(5.498F, -30.8743F, 0.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition cube_r32 = group_90_13.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(56, 57).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.007F)), PartPose.offsetAndRotation(5.2123F, -31.2467F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r33 = group_90_13.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(42, 57).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.006F)), PartPose.offsetAndRotation(4.84F, -31.5324F, 0.0F, 0.0F, 0.0F, -1.0472F));

        PartDefinition cube_r34 = group_90_13.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(28, 57).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.005F)), PartPose.offsetAndRotation(4.4064F, -31.712F, 0.0F, 0.0F, 0.0F, -1.309F));

        PartDefinition cube_r35 = group_90_13.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(56, 52).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.004F)), PartPose.offsetAndRotation(3.7316F, -31.9915F, 0.0F, 0.0F, 0.0F, -1.0472F));

        PartDefinition cube_r36 = group_90_13.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(56, 47).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.003F)), PartPose.offsetAndRotation(3.1521F, -32.4361F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r37 = group_90_13.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(14, 53).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.002F)), PartPose.offsetAndRotation(2.7075F, -33.0156F, 0.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition cube_r38 = group_90_13.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(52, 42).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(2.428F, -33.6904F, 0.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition group_90_14 = kurve_oben_rechts.addOrReplaceChild("90_14", CubeListBuilder.create(), PartPose.offset(0.0716F, 35.3188F, 0.0F));

        PartDefinition cube_r39 = group_90_14.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(64, 5).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(4.7042F, -32.4141F, 0.0F, 0.0F, 0.0F, -1.309F));

        PartDefinition cube_r40 = group_90_14.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(64, 0).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.002F)), PartPose.offsetAndRotation(4.0294F, -32.6936F, 0.0F, 0.0F, 0.0F, -1.0472F));

        PartDefinition cube_r41 = group_90_14.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(14, 63).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.003F)), PartPose.offsetAndRotation(3.45F, -33.1382F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r42 = group_90_14.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(56, 62).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.004F)), PartPose.offsetAndRotation(3.0053F, -33.7177F, 0.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition cube_r43 = group_90_14.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(42, 62).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(2.2605F, -35.1985F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r44 = group_90_14.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(62, 30).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(2.5462F, -34.8261F, 0.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition cube_r45 = group_90_14.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(28, 62).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.005F)), PartPose.offsetAndRotation(2.7258F, -34.3925F, 0.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition kurve_oben_links = Portal.addOrReplaceChild("kurve_oben_links", CubeListBuilder.create(), PartPose.offsetAndRotation(7.9167F, -15.1667F, 0.0F, 0.0F, 0.0F, 3.1416F));

        PartDefinition group_90_15 = kurve_oben_links.addOrReplaceChild("90_15", CubeListBuilder.create(), PartPose.offset(2.0716F, -11.6812F, 0.0F));

        PartDefinition cube_r46 = group_90_15.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(28, 67).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.008F)), PartPose.offsetAndRotation(-2.0977F, 11.705F, 0.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition cube_r47 = group_90_15.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(66, 40).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.007F)), PartPose.offsetAndRotation(-2.3834F, 11.3326F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r48 = group_90_15.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(66, 35).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.006F)), PartPose.offsetAndRotation(-2.7558F, 11.0469F, 0.0F, 0.0F, 0.0F, -1.0472F));

        PartDefinition cube_r49 = group_90_15.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(0, 66).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.005F)), PartPose.offsetAndRotation(-3.1894F, 10.8673F, 0.0F, 0.0F, 0.0F, -1.309F));

        PartDefinition cube_r50 = group_90_15.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(64, 25).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.004F)), PartPose.offsetAndRotation(-3.8642F, 10.5878F, 0.0F, 0.0F, 0.0F, -1.0472F));

        PartDefinition cube_r51 = group_90_15.addOrReplaceChild("cube_r51", CubeListBuilder.create().texOffs(64, 20).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.003F)), PartPose.offsetAndRotation(-4.4437F, 10.1431F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r52 = group_90_15.addOrReplaceChild("cube_r52", CubeListBuilder.create().texOffs(64, 15).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.002F)), PartPose.offsetAndRotation(-4.8883F, 9.5637F, 0.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition cube_r53 = group_90_15.addOrReplaceChild("cube_r53", CubeListBuilder.create().texOffs(64, 10).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(-5.1678F, 8.8889F, 0.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition group_90_16 = kurve_oben_links.addOrReplaceChild("90_16", CubeListBuilder.create(), PartPose.offset(5.0716F, -7.6812F, 0.0F));

        PartDefinition cube_r54 = group_90_16.addOrReplaceChild("cube_r54", CubeListBuilder.create().texOffs(70, 60).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(-2.8915F, 10.1652F, 0.0F, 0.0F, 0.0F, -1.309F));

        PartDefinition cube_r55 = group_90_16.addOrReplaceChild("cube_r55", CubeListBuilder.create().texOffs(70, 55).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.002F)), PartPose.offsetAndRotation(-3.5663F, 9.8856F, 0.0F, 0.0F, 0.0F, -1.0472F));

        PartDefinition cube_r56 = group_90_16.addOrReplaceChild("cube_r56", CubeListBuilder.create().texOffs(70, 50).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.003F)), PartPose.offsetAndRotation(-4.1458F, 9.441F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r57 = group_90_16.addOrReplaceChild("cube_r57", CubeListBuilder.create().texOffs(70, 45).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.004F)), PartPose.offsetAndRotation(-4.5904F, 8.8616F, 0.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition cube_r58 = group_90_16.addOrReplaceChild("cube_r58", CubeListBuilder.create().texOffs(14, 68).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.005F)), PartPose.offsetAndRotation(-5.3352F, 7.3808F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r59 = group_90_16.addOrReplaceChild("cube_r59", CubeListBuilder.create().texOffs(56, 67).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.006F)), PartPose.offsetAndRotation(-5.0495F, 7.7531F, 0.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition cube_r60 = group_90_16.addOrReplaceChild("cube_r60", CubeListBuilder.create().texOffs(42, 67).addBox(-1.0F, -0.5F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.007F)), PartPose.offsetAndRotation(-4.8699F, 8.1868F, 0.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition Inneres = Portal.addOrReplaceChild("Inneres", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -36.0F, -1.0F, 16.0F, 34.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.9167F, 19.8333F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(PortalRenderState renderState) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.spawnAnimation.apply(renderState.spawnAnimationState, renderState.ageInTicks, 1f);
        this.idleAnimation.apply(renderState.idleAnimationState, renderState.ageInTicks, 1f);
    }
}
