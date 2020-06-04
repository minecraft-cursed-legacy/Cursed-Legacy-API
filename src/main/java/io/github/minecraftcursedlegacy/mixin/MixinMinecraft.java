package io.github.minecraftcursedlegacy.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import io.github.minecraftcursedlegacy.api.event.ClientTickCallback;
import net.minecraft.client.Minecraft;

@Mixin(Minecraft.class)
public class MixinMinecraft {
	@Inject(at = @At("RETURN"), method = "tick")
	private void onTick() {
		ClientTickCallback.EVENT.invoker().onClientTick((Minecraft) (Object) this);
	}
}
