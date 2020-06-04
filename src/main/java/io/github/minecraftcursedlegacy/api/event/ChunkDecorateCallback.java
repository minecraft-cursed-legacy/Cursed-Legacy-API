package io.github.minecraftcursedlegacy.api.event;

import java.util.Random;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.level.Level;
import net.minecraft.level.biome.Biome;

/**
 * Callback for chunk decoration.
 */
@FunctionalInterface
public interface ChunkDecorateCallback {
	/**
	 * The ChunkDecorateCallback for the overworld dimension.
	 */
	Event<ChunkDecorateCallback> OVERWORLD = EventFactory.createArrayBacked(ChunkDecorateCallback.class,
			(listeners) -> (level, biome, rand, x, z) -> {
				for (ChunkDecorateCallback listener : listeners) {
					listener.onDecorate(level, biome, rand, x, z);
				}
			});

	/**
	 * The ChunkDecorateCallback for the nether dimension.
	 */
	Event<ChunkDecorateCallback> NETHER = EventFactory.createArrayBacked(ChunkDecorateCallback.class,
			(listeners) -> (level, biome, rand, x, z) -> {
				for (ChunkDecorateCallback listener : listeners) {
					listener.onDecorate(level, biome, rand, x, z);
				}
			});
	/**
	 * @deprecated use the dimension specific event instead.
	 */
	@Deprecated
	Event<ChunkDecorateCallback> EVENT = OVERWORLD;

	void onDecorate(Level level, Biome biome, Random rand, int x, int z);
}
