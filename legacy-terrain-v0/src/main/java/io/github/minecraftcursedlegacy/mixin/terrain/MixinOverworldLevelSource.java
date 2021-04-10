package io.github.minecraftcursedlegacy.mixin.terrain;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.minecraftcursedlegacy.api.levelgen.ChunkDecorateCallback;
import io.github.minecraftcursedlegacy.api.levelgen.ChunkShapeCallback;
import net.minecraft.level.Level;
import net.minecraft.level.biome.Biome;
import net.minecraft.level.source.LevelSource;
import net.minecraft.level.source.OverworldLevelSource;

@Mixin(OverworldLevelSource.class)
public class MixinOverworldLevelSource {
	@Shadow
	private Random rand;
	@Shadow
	private Level level;

	@Inject(at = @At(value = "INVOKE", target = "net/minecraft/level/Level.getBiomeSource()Lnet/minecraft/level/gen/BiomeSource;", ordinal = 1), method = "decorate")
	private void onDecorate(LevelSource levelSource, int x, int z, CallbackInfo info) {
		// the parameter variables x and z are multiplied by 16 in the code early on
		// but for some reason it doesn't seem to affect this
		x *= 16;
		z *= 16;
		ChunkDecorateCallback.OVERWORLD.invoker().onDecorate(
				this.level,
				this.level.getBiomeSource().getBiome(x + 16, z + 16),
				this.rand,
				x,
				z);
	}

	@Inject(at = @At("RETURN"), method = "shapeChunk")
	private void shapeChunk(int chunkX, int chunkZ, byte[] tiles, Biome[] biomes, double[] temperatures, CallbackInfo info) {
		ChunkShapeCallback.OVERWORLD.invoker().onShape(chunkX, chunkZ, tiles, biomes, temperatures);
	}
}
