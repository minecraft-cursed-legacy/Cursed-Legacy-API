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
	Event<ChunkDecorateCallback> EVENT = EventFactory.createArrayBacked(ChunkDecorateCallback.class,
			(listeners) -> (level, biome, rand, x, z) -> {
				for (ChunkDecorateCallback listener : listeners) {
					listener.onDecorate(level, biome, rand, x, z);
				}
			});

	void onDecorate(Level level, Biome biome, Random rand, int x, int z);
}
