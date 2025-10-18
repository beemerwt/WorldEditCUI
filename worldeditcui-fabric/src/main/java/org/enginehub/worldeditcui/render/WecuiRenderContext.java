/*
 * Copyright (c) 2011-2024 WorldEditCUI team and contributors
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.enginehub.worldeditcui.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.GraphicsStatus;
import net.minecraft.client.Minecraft;
import org.joml.Matrix4f;

public record WecuiRenderContext(
        Minecraft client,
        Camera camera,
        DeltaTracker delta,
        PoseStack poseStack,
        Matrix4f projectionMatrix
) {
    public boolean advancedTranslucency() {
        var status = client.options.graphicsMode().get();
        return status == GraphicsStatus.FABULOUS;
    }
}