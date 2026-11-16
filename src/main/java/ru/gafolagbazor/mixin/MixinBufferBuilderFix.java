package ru.gafolagbazor.mixin;

import net.irisshaders.iris.vertices.NormalHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = NormalHelper.class, remap = false)
public abstract class MixinBufferBuilderFix {
    @Inject(method = "computeTangent(FFFFFFFFFFFFFFFFFF)I", at = @At("HEAD"), cancellable = true)
    private static void safeTriangleTangent(float normalX, float normalY, float normalZ,
                                            float x0, float y0, float z0, float u0, float v0,
                                            float x1, float y1, float z1, float u1, float v1,
                                            float x2, float y2, float z2, float u2, float v2,
                                            CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(0);
    }
}
