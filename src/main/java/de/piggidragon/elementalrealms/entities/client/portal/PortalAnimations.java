package de.piggidragon.elementalrealms.entities.client.portal;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class PortalAnimations {
    public static final AnimationDefinition ANIMATION_IDLE_HOVER = AnimationDefinition.Builder.withLength(8.0F).looping()
            .addAnimation("Portal", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 2.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(1.2F, KeyframeAnimations.posVec(-0.04F, 2.35F, -0.03F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(3.0F, KeyframeAnimations.posVec(0.03F, 2.0F, 0.03F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(4.5F, KeyframeAnimations.posVec(-0.05F, 1.7F, -0.04F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(6.5F, KeyframeAnimations.posVec(0.04F, 2.2F, 0.02F), AnimationChannel.Interpolations.CATMULLROM),
                    new Keyframe(8.0F, KeyframeAnimations.posVec(0.0F, 2.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
            ))
            .build();

    public static final AnimationDefinition ANIMATION_SPAWN = AnimationDefinition.Builder.withLength(0.5F)
            .addAnimation("Portal", new AnimationChannel(AnimationChannel.Targets.ROTATION,
                    new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 180.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -180.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Portal", new AnimationChannel(AnimationChannel.Targets.POSITION,
                    new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 2.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addAnimation("Portal", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.25F, KeyframeAnimations.scaleVec(0.25F, 0.25F, 0.25F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(0.5F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();
}
