package io.github.minecraftcursedlegacy.event.lifecycle.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.minecraftcursedlegacy.api.event.lifecycle.ClientTickCallback;
import net.minecraft.client.Minecraft;

@Mixin(Minecraft.class)
public class MixinMinecraft {
	@Inject(at = @At("RETURN"), method = "tick")
	private void onTick(CallbackInfo info) {
		ClientTickCallback.EVENT.invoker().onClientTick((Minecraft) (Object) this);
	}
}
