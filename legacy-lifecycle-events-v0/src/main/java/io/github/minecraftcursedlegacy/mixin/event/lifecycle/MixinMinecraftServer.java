package io.github.minecraftcursedlegacy.mixin.event.lifecycle;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.minecraftcursedlegacy.api.event.lifecycle.DedicatedServerTickCallback;
import net.minecraft.server.MinecraftServer;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {
	// MinecraftServer#tick()
	@Inject(at = @At("RETURN"), method = "method_2171")
	private void onTick(CallbackInfo info) {
		DedicatedServerTickCallback.EVENT.invoker().onServerTick((MinecraftServer) (Object) this);
	}
}
