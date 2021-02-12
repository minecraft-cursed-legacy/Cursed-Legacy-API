package io.github.minecraftcursedlegacy.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.minecraftcursedlegacy.api.event.ActionResult;
import io.github.minecraftcursedlegacy.api.levelgen.BiomePlacementCallback;
import io.github.minecraftcursedlegacy.impl.event.Wrapper;
import net.minecraft.level.biome.Biome;

@Mixin(Biome.class)
public class MixinBiome {
	@Inject(at = @At("HEAD"), method = "getClimateBiome", cancellable = true)
	private static void addModdedBiomeGen(float temperature, float humidity, CallbackInfoReturnable<Biome> info) {
		Wrapper<Biome> biomeResultWrapper = new Wrapper<>();
		ActionResult result = BiomePlacementCallback.EVENT.invoker().onBiomePlace(temperature, humidity, biomeResultWrapper::setValue);

		if (result == ActionResult.SUCCESS) {
			Biome biomeResult = biomeResultWrapper.getValue();

			if (biomeResult != null) {
				info.setReturnValue(biomeResult);
			}
		}
	}
}
