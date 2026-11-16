package ru.gafolagbazor;

import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import net.irisshaders.iris.pipeline.programs.ShaderKey;
import net.irisshaders.iris.shadows.ShadowRenderingState;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public final class RealMixin {
    @SuppressWarnings("unchecked")
    public static void resolvePipeline(Object pipeline, Object shaderObj, Object cirObj) {
        CallbackInfoReturnable<ShaderKey> cir = (CallbackInfoReturnable<ShaderKey>) cirObj;
        RenderPipeline shader = (RenderPipeline) shaderObj;
        if (cir.getReturnValue() == null && shader != null) {
            String path = shader.getLocation().getPath();
            if (path.contains("entity_translucent_triangles")) {
                cir.setReturnValue(ShadowRenderingState.areShadowsCurrentlyBeingRendered()
                        ? ShaderKey.SHADOW_ENTITIES_CUTOUT
                        : ShaderKey.ENTITIES_TRANSLUCENT);
            } else if (path.contains("entity_cutout_triangles")) {
                cir.setReturnValue(ShadowRenderingState.areShadowsCurrentlyBeingRendered()
                        ? ShaderKey.SHADOW_ENTITIES_CUTOUT
                        : ShaderKey.ENTITIES_CUTOUT_DIFFUSE);
            }
        }
    }
}
