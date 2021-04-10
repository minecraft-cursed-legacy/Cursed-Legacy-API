package io.github.minecraftcursedlegacy.terrain.impl;

import io.github.minecraftcursedlegacy.api.ModPostInitializer;
import net.minecraft.level.biome.Biome;

public class BiomeRecomputer implements ModPostInitializer {
	@Override
	public void onPostInitialize() {
		Biome.createBiomeArray();
	}
}
