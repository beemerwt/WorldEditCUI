/*
 * Copyright (c) 2011-2024 WorldEditCUI team and contributors
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.enginehub.worldeditcui.mixin;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.resource.GraphicsResourceAllocator;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.state.BlockOutlineRenderState;
import org.enginehub.worldeditcui.callback.BlockOutlineRenderCallback;
import org.enginehub.worldeditcui.callback.WorldRenderCallback;
import org.enginehub.worldeditcui.render.WecuiRenderContext;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {
    @Shadow @Final private Minecraft minecraft;

    // START (old START)
    @Inject(method = "renderLevel", at = @At("HEAD"))
    private void wecui$start(
            GraphicsResourceAllocator gfx, DeltaTracker delta, boolean renderBlockOutline,
            Camera camera, Matrix4f modelView, Matrix4f projection, Matrix4f inverseProjection,
            GpuBufferSlice slice, Vector4f clearColor, boolean renderSky, CallbackInfo ci
    ) {
        // per-frame prep (camera/projection etc.)
        // modelView/projection are valid right here in 1.21.9
    }

    // LAST (old LAST)
    @Inject(method = "renderLevel", at = @At("TAIL"))
    private void wecui$last(
            GraphicsResourceAllocator gfx, DeltaTracker delta, boolean renderBlockOutline,
            Camera camera, Matrix4f modelView, Matrix4f projection, Matrix4f inverseProjection,
            GpuBufferSlice slice, Vector4f clearColor, boolean renderSky, CallbackInfo ci
    ) {
        PoseStack pose = new PoseStack();
        pose.last().pose().set(modelView); // copy modelView for parity with old ctx
        WorldRenderCallback.LAST.invoker()
                .render(new WecuiRenderContext(this.minecraft, camera, delta, pose, projection));
    }

    // BEFORE_ENTITIES → right BEFORE extractVisibleEntities(...)
    @Inject(
            method = "renderLevel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/LevelRenderer;extractVisibleEntities(" +
                            "Lnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/culling/Frustum;" +
                            "Lnet/minecraft/client/DeltaTracker;Lnet/minecraft/client/renderer/state/LevelRenderState;)V"
            )
    )
    private void wecui$beforeEntities(
            GraphicsResourceAllocator gfx, DeltaTracker delta, boolean renderBlockOutline,
            Camera camera, Matrix4f modelView, Matrix4f projection, Matrix4f inverseProjection,
            GpuBufferSlice slice, Vector4f clearColor, boolean renderSky,
            CallbackInfo ci
            // Locals in scope at this point (order matters; keep in sync with the method body you posted)
    ) {
        var pose = new PoseStack();
        pose.last().pose().set(modelView);
        WorldRenderCallback.BEFORE_ENTITY_EXTRACT.invoker()
                .render(new WecuiRenderContext(this.minecraft, camera, delta, pose, projection));
    }

    // AFTER_ENTITIES → right AFTER extractVisibleEntities(...)
    @Inject(
            method = "renderLevel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/LevelRenderer;extractVisibleEntities(" +
                            "Lnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/culling/Frustum;" +
                            "Lnet/minecraft/client/DeltaTracker;Lnet/minecraft/client/renderer/state/LevelRenderState;)V",
                    shift = At.Shift.AFTER
            )
    )
    private void wecui$afterEntities(
            GraphicsResourceAllocator gfx, DeltaTracker delta, boolean renderBlockOutline,
            Camera camera, Matrix4f modelView, Matrix4f projection, Matrix4f inverseProjection,
            GpuBufferSlice slice, Vector4f clearColor, boolean renderSky,
            CallbackInfo ci
    ) {
        var pose = new PoseStack();
        pose.last().pose().set(modelView);
        WorldRenderCallback.AFTER_ENTITY_EXTRACT.invoker()
                .render(new WecuiRenderContext(this.minecraft, camera, delta, pose, projection));
    }

    // BEFORE_BLOCK_ENTITY_EXTRACT
    @Inject(
            method = "renderLevel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/LevelRenderer;extractVisibleBlockEntities(" +
                            "Lnet/minecraft/client/Camera;FLnet/minecraft/client/renderer/state/LevelRenderState;)V"
            )
    )
    private void wecui$beforeBlockEntityExtract(
            GraphicsResourceAllocator gfx, DeltaTracker delta, boolean renderBlockOutline,
            Camera camera, Matrix4f modelView, Matrix4f projection, Matrix4f inverseProjection,
            GpuBufferSlice slice, Vector4f clearColor, boolean renderSky,
            CallbackInfo ci
    ) {
        var pose = new PoseStack();
        pose.last().pose().set(modelView);
        WorldRenderCallback.BEFORE_BLOCK_ENTITY_EXTRACT.invoker()
                .render(new WecuiRenderContext(this.minecraft, camera, delta, pose, projection));
    }

    // AFTER_BLOCK_ENTITY_EXTRACT
    @Inject(
            method = "renderLevel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/LevelRenderer;extractVisibleBlockEntities(" +
                            "Lnet/minecraft/client/Camera;FLnet/minecraft/client/renderer/state/LevelRenderState;)V",
                    shift = At.Shift.AFTER
            )
    )
    private void wecui$afterBlockEntityExtract(
            GraphicsResourceAllocator gfx, DeltaTracker delta, boolean renderBlockOutline,
            Camera camera, Matrix4f modelView, Matrix4f projection, Matrix4f inverseProjection,
            GpuBufferSlice slice, Vector4f clearColor, boolean renderSky,
            CallbackInfo ci
    ) {
        var pose = new PoseStack();
        pose.last().pose().set(modelView);
        WorldRenderCallback.AFTER_BLOCK_ENTITY_EXTRACT.invoker()
                .render(new WecuiRenderContext(this.minecraft, camera, delta, pose, projection));
    }

    @Inject(
            method = "renderLevel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/LevelRenderer;extractBlockOutline(" +
                            "Lnet/minecraft/client/Camera;" +
                            "Lnet/minecraft/client/renderer/state/LevelRenderState;)V"
            )
    )
    private void wecui$beforeBlockOutlineExtract(
            GraphicsResourceAllocator gfx, DeltaTracker delta, boolean renderBlockOutline,
            Camera camera, Matrix4f modelView, Matrix4f projection, Matrix4f inverseProjection,
            GpuBufferSlice slice, Vector4f clearColor, boolean renderSky,
            CallbackInfo ci
    ) {
        var pose = new PoseStack();
        pose.last().pose().set(modelView);
        WorldRenderCallback.BEFORE_BLOCK_OUTLINE_EXTRACT.invoker()
                .render(new WecuiRenderContext(this.minecraft, camera, delta, pose, projection));
    }

    @Inject(method = "renderHitOutline", at = @At("HEAD"), cancellable = true)
    private void wecui$blockOutline(
            PoseStack pose, VertexConsumer consumer,
            double camX, double camY, double camZ,
            BlockOutlineRenderState state, int packedLight,
            CallbackInfo ci
    ) {
        var ctx = new BlockOutlineRenderCallback.Context(
                this.minecraft,
                this.minecraft.gameRenderer.getMainCamera(),
                pose,
                RenderSystem.getProjectionMatrixBuffer(),
                consumer,
                camX, camY, camZ,
                state
        );
        if (BlockOutlineRenderCallback.EVENT.invoker().render(ctx)) {
            ci.cancel(); // fully replace vanilla outline
        }
    }

    @Inject(
            method = "renderLevel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/LevelRenderer;addMainPass(Lcom/mojang/blaze3d/framegraph/FrameGraphBuilder;Lnet/minecraft/client/renderer/culling/Frustum;Lorg/joml/Matrix4f;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;ZLnet/minecraft/client/renderer/state/LevelRenderState;Lnet/minecraft/client/DeltaTracker;Lnet/minecraft/util/profiling/ProfilerFiller;)V",
                    shift = At.Shift.AFTER
            )
    )
    private void wecui$afterMainPassScheduled(
            GraphicsResourceAllocator gfx, DeltaTracker delta, boolean renderBlockOutline,
            Camera camera, Matrix4f modelView, Matrix4f projection, Matrix4f inverseProjection,
            GpuBufferSlice slice, Vector4f clearColor, boolean renderSky,
            CallbackInfo ci
    ) {
        var pose = new PoseStack();
        pose.last().pose().set(modelView);
        WorldRenderCallback.AFTER_MAIN_PASS_SCHEDULED.invoker()
                .render(new WecuiRenderContext(this.minecraft, camera, delta, pose, projection));
    }

    // AFTER_TRANSLUCENT — fires immediately after translucent pass queued/executed
    @Inject(
            method = "renderLevel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/LevelRenderer;addParticlesPass(Lcom/mojang/blaze3d/framegraph/FrameGraphBuilder;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;)V"
            )
    )
    private void wecui$afterTranslucent(
            GraphicsResourceAllocator gfx, DeltaTracker delta, boolean renderBlockOutline,
            Camera camera, Matrix4f modelView, Matrix4f projection, Matrix4f inverseProjection,
            GpuBufferSlice slice, Vector4f clearColor, boolean renderSky,
            CallbackInfo ci
    ) {
        var pose = new PoseStack();
        pose.last().pose().set(modelView);
        WorldRenderCallback.AFTER_TRANSLUCENT.invoker()
                .render(new WecuiRenderContext(this.minecraft, camera, delta, pose, projection));
    }

}
