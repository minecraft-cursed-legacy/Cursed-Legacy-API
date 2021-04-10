package io.github.minecraftcursedlegacy.terrain.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.minecraftcursedlegacy.api.levelgen.ChunkDecorateCallback;
import net.minecraft.level.Level;
import net.minecraft.level.source.LevelSource;
import net.minecraft.level.source.NetherLevelSource;
import net.minecraft.tile.Sand;

@Mixin(NetherLevelSource.class)
public class MixinNetherLevelSource {
	@Shadow
	private Random rand;
	@Shadow
	private Level level;

	@Inject(at = @At("RETURN"), method = "decorate")
	private void onDecorate(LevelSource levelSource, int x, int z, CallbackInfo info) {
		Sand.fallInstantly = true;
		x *= 16;
		z *= 16;

		ChunkDecorateCallback.NETHER.invoker().onDecorate(
				this.level,
				this.level.getBiomeSource().getBiome(x + 16, z + 16),
				this.rand,
				x,
				z);

		Sand.fallInstantly = false;
	}
}
