/*
 * Copyright (c) 2011-2024 WorldEditCUI team and contributors
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.enginehub.worldeditcui.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import org.enginehub.worldeditcui.render.WecuiRenderContext;

public interface WorldRenderCallback {
    void render(WecuiRenderContext ctx);

    Event<WorldRenderCallback> LAST = EventFactory.createArrayBacked(WorldRenderCallback.class, cbs -> ctx -> {
        for (var cb : cbs) cb.render(ctx);
    });
    Event<WorldRenderCallback> START = EventFactory.createArrayBacked(WorldRenderCallback.class, cbs -> ctx -> {
        for (var cb : cbs) cb.render(ctx);
    });
    Event<WorldRenderCallback> BEFORE_ENTITY_EXTRACT = EventFactory.createArrayBacked(WorldRenderCallback.class, cbs -> ctx -> {
        for (var cb : cbs) cb.render(ctx);
    });
    Event<WorldRenderCallback> AFTER_ENTITY_EXTRACT = EventFactory.createArrayBacked(WorldRenderCallback.class, cbs -> ctx -> {
        for (var cb : cbs) cb.render(ctx);
    });
    Event<WorldRenderCallback> BEFORE_BLOCK_ENTITY_EXTRACT = EventFactory.createArrayBacked(WorldRenderCallback.class, cbs -> ctx -> {
        for (var cb : cbs) cb.render(ctx);
    });
    Event<WorldRenderCallback> AFTER_BLOCK_ENTITY_EXTRACT = EventFactory.createArrayBacked(WorldRenderCallback.class, cbs -> ctx -> {
        for (var cb : cbs) cb.render(ctx);
    });
    Event<WorldRenderCallback> BEFORE_BLOCK_OUTLINE_EXTRACT = EventFactory.createArrayBacked(WorldRenderCallback.class, cbs -> ctx -> {
        for (var cb : cbs) cb.render(ctx);
    });
    Event<WorldRenderCallback> AFTER_TRANSLUCENT = EventFactory.createArrayBacked(WorldRenderCallback.class, cbs -> ctx -> {
        for (var cb : cbs) cb.render(ctx);
    });
    Event<WorldRenderCallback> AFTER_MAIN_PASS_SCHEDULED = EventFactory.createArrayBacked(WorldRenderCallback.class, cbs -> ctx -> {
        for (var cb : cbs) cb.render(ctx);
    });
}
