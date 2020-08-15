package io.github.minecraftcursedlegacy.impl.levelgen;

import io.github.minecraftcursedlegacy.api.event.ChunkDecorateCallback;
import io.github.minecraftcursedlegacy.api.levelgen.ExtendedBiome;
import net.fabricmc.api.ModInitializer;
import net.minecraft.level.structure.Feature;

public class LevelGenImpl implements ModInitializer {
	@Override
	public void onInitialize() {
		ChunkDecorateCallback decoration = (level, biome, rand, x, z) -> {
			if (biome instanceof ExtendedBiome) {
				ExtendedBiome eBiome = (ExtendedBiome) biome;

				for(int i = 0; i < eBiome.getTreesPerChunk(); ++i) {
					int xToGen = x + rand.nextInt(16) + 8;
					int zToGen = z + rand.nextInt(16) + 8;
					Feature var18 = biome.getTree(rand);
					var18.setupTreeGeneration(1.0D, 1.0D, 1.0D);
					var18.generate(level, rand, xToGen, level.getHeight(xToGen, zToGen), zToGen);
				}
			}
		};

		ChunkDecorateCallback.OVERWORLD.register(decoration);
		ChunkDecorateCallback.NETHER.register(decoration);
	}
}
