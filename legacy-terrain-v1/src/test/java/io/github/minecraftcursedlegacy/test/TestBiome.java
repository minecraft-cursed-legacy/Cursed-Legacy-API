package io.github.minecraftcursedlegacy.test;

import java.util.Random;

import io.github.minecraftcursedlegacy.api.levelgen.ExtendedBiome;
import net.minecraft.level.biome.Biome;
import net.minecraft.level.structure.Feature;
import net.minecraft.level.structure.Spruce;

public class TestBiome extends Biome implements ExtendedBiome {
	public TestBiome(String name) {
		this.setGrassColour(14278691);
		this.setName(name);
	}

	@Override
	public Feature getTree(Random random) {
		return new Spruce();
	}

	@Override
	public int getTreesPerChunk() {
		return 1;
	}
}
