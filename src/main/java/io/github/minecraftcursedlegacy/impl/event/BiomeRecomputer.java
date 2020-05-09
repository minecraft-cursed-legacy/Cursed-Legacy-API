package io.github.minecraftcursedlegacy.impl.event;

import io.github.minecraftcursedlegacy.api.ModPostInitializer;
import net.minecraft.level.biome.Biome;

public class BiomeRecomputer implements ModPostInitializer {
	@Override
	public void onPostInitialize() {
		Biome.createBiomeArray();
	}
}
