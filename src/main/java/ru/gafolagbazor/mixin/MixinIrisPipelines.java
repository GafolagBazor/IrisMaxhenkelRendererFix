package ru.gafolagbazor.mixin;

import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import net.irisshaders.iris.pipeline.IrisPipelines;
import net.irisshaders.iris.pipeline.IrisRenderingPipeline;
import net.irisshaders.iris.pipeline.programs.ShaderKey;
import net.neoforged.fml.loading.FMLEnvironment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.gafolagbazor.IrisFix;
import ru.gafolagbazor.RealMixin;

@Mixin(value = IrisPipelines.class, remap = false)
public abstract class MixinIrisPipelines {
    @Inject(method = "getPipeline", at = @At("RETURN"), cancellable = true)
    private static void fixCustomTrianglePipelines(IrisRenderingPipeline pipeline, RenderPipeline shader, CallbackInfoReturnable<ShaderKey> cir) throws Throwable {
        if (FMLEnvironment.isProduction()) {
            IrisFix.PIPELINE_HANDLE.invokeExact((Object) pipeline, (Object) shader, (Object) cir);
        } else {
            RealMixin.resolvePipeline(pipeline, shader, cir);
        }
    }
}
