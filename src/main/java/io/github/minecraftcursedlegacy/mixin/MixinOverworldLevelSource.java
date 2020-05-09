package io.github.minecraftcursedlegacy.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.minecraftcursedlegacy.api.event.ChunkDecorateCallback;
import net.minecraft.level.Level;
import net.minecraft.level.source.LevelSource;
import net.minecraft.level.source.OverworldLevelSource;

@Mixin(OverworldLevelSource.class)
public class MixinOverworldLevelSource {
	@Shadow
	private Random rand;
	@Shadow
	private Level field_2260;

	@Inject(at = @At(value = "INVOKE", target = " net/minecraft/level/Level.getBiomeSource()Lnet/minecraft/level/gen/BiomeSource;", ordinal = 1), method = "decorate")
	private void onDecorate(LevelSource levelSource, int x, int z, CallbackInfo info) {
		// the parameter variables x and z are multiplied by 16 in the code early on
		// but for some reason it doesn't seem to affect this
		x *= 16;
		z *= 16;
		ChunkDecorateCallback.EVENT.invoker().onDecorate(
				this.field_2260,
				this.field_2260.getBiomeSource().getBiome(x + 16, z + 16),
				this.rand,
				x,
				z);
	}
}
