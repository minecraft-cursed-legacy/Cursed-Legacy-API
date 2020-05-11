package io.github.minecraftcursedlegacy.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.minecraftcursedlegacy.impl.vanillachecker.VanillaCheckerImpl;
import net.minecraft.class_11;
import net.minecraft.entity.player.ServerPlayer;

@Mixin(class_11.class)
public class MixinClass_11 {
    @Shadow
    private ServerPlayer field_920;

    @Inject(at= @At("HEAD"), method = "method_1473")
    public void method_1473(String string, Object[] objects, CallbackInfo bruh) {
        VanillaCheckerImpl.playermap.remove(field_920.name);
    }
}