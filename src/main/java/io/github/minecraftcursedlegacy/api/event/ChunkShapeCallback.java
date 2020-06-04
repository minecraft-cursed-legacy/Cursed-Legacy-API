package io.github.minecraftcursedlegacy.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.level.biome.Biome;

/**
 * Callback for chunk shaping.
 */
@FunctionalInterface
public interface ChunkShapeCallback {
	Event<ChunkShapeCallback> OVERWORLD = EventFactory.createArrayBacked(ChunkShapeCallback.class,
			(listeners) -> (level, biome, rand, x, z) -> {
				for (ChunkShapeCallback listener : listeners) {
					listener.onShape(level, biome, rand, x, z);
				}
			});

	/**
	 * Called after the chunk is shaped.
	 *
	 * @param chunkX the x position of the chunk in the chunk grid.
	 * @param chunkZ the z position of the chunk in the chunk grid.
	 * @param tiles the byte array representing the ids of the tiles in the chunk.
	 */
	void onShape(int chunkX, int chunkZ, byte[] tiles, Biome[] biomes, double[] temperatures);
}
