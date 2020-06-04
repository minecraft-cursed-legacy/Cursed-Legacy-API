package io.github.minecraftcursedlegacy.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import io.github.minecraftcursedlegacy.api.event.DedicatedServerTickCallback;
import net.minecraft.server.MinecraftServer;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {
	// MinecraftServer#tick()
	@Inject(at = @At("RETURN"), method = "method_2171")
	private void onTick() {
		DedicatedServerTickCallback.EVENT.invoker().onServerTick((MinecraftServer) (Object) this);
	}
}
