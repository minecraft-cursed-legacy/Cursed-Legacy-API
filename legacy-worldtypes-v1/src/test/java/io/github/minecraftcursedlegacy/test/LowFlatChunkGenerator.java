package io.github.minecraftcursedlegacy.test;

import io.github.minecraftcursedlegacy.api.terrain.ChunkGenerator;
import net.minecraft.level.Level;
import net.minecraft.level.biome.Biome;
import net.minecraft.level.source.LevelSource;
import net.minecraft.level.source.OverworldLevelSource;
import net.minecraft.tile.Tile;

public class LowFlatChunkGenerator extends ChunkGenerator {
	public LowFlatChunkGenerator(Level level, long seed) {
		super(level, seed);
		this.surface = new OverworldLevelSource(level, seed);
	}

	private final OverworldLevelSource surface;

	@Override
	public void decorate(LevelSource levelSource, int chunkX, int chunkZ) {
		this.surface.decorate(levelSource, chunkX, chunkZ);
	}

	@Override
	protected void shapeChunk(int chunkX, int chunkZ, byte[] tiles, Biome[] biomes) {
		for (int localX = 0; localX < 16; ++localX) {
			for (int localZ = 0; localZ < 16; ++localZ) {
				int height = 10;

				for (int y = height; y >= 0; --y) {
					tiles[getIndex(localX, y, localZ)] = (byte) Tile.STONE.id;
				}
			}
		}
	}

	@Override
	protected void buildSurface(int chunkX, int chunkZ, byte[] tiles, Biome[] biomes) {
		this.surface.buildSurface(chunkX, chunkZ, tiles, biomes);
	}

	@Override
	public int getMinSpawnY() {
		return 9;
	}

	@Override
	public boolean isValidSpawnPos(int x, int z) {
		int surfaceTile = this.level.method_152(x, z);
		return surfaceTile == Tile.GRASS.id;
	}
}
