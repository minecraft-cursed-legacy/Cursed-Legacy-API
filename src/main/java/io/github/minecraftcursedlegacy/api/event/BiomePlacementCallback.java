package io.github.minecraftcursedlegacy.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.level.biome.Biome;

import java.util.function.Consumer;

/**
 * Callback for biome placement. Add a hook for this in the {@link net.fabricmc.api.ModInitializer init} stage, as the biomes are calculated in postinit.
 * 
 * <p>Upon return:
 * <ul>
 * <li> SUCCESS succeeds in altering the biome, and sets the latest biome set via the biome setter. If no biome has been set, then the behaviour defaults to FAIL
 * <li> PASS falls back to further event processing. If all events PASS, then the behaviour defaults to SUCCESS.
 * <li> FAIL falls back to vanilla biome placement.
 * </ul>
 */
@FunctionalInterface
public interface BiomePlacementCallback {
	Event<BiomePlacementCallback> EVENT = EventFactory.createArrayBacked(BiomePlacementCallback.class,
			(listeners) -> (temperature, rainfall, biomeSetter) -> {
				for (BiomePlacementCallback listener : listeners) {
					ActionResult result = listener.onBiomePlace(temperature, rainfall, biomeSetter);

					if (result != ActionResult.PASS) {
						return result;
					}
				}

				return ActionResult.SUCCESS;
			});

	ActionResult onBiomePlace(float temperature, float humidity, Consumer<Biome> biomeSetter);
}
