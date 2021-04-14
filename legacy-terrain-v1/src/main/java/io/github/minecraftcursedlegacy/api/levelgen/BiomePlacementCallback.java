package io.github.minecraftcursedlegacy.api.levelgen;

import java.util.function.Consumer;

import io.github.minecraftcursedlegacy.api.event.ActionResult;
import io.github.minecraftcursedlegacy.api.terrain.BiomeEvents;
import net.minecraft.level.biome.Biome;

/**
 * Callback for biome placement. Add a hook for this in the {@link net.fabricmc.api.ModInitializer init} stage, as the biomes are calculated in postinit.
 * 
 * <p>Upon return:
 * <ul>
 * <li> SUCCESS succeeds in altering the biome, and sets the latest biome set via the biome setter. If no biome has been set, then the behaviour defaults to FAIL
 * <li> PASS falls back to further event processing. If all events PASS, then the behaviour defaults to SUCCESS.
 * <li> FAIL falls back to vanilla biome placement.
 * </ul>
 * 
 * @deprecated use {@linkplain BiomeEvents.BiomePlacementCallback instead}.
 */
@FunctionalInterface
@Deprecated
public interface BiomePlacementCallback extends BiomeEvents.BiomePlacementCallback {
	@Override
	default ActionResult onClimaticBiomePlace(float temperature, float humidity, Consumer<Biome> biomeSetter) {
		return this.onBiomePlace(temperature, humidity, biomeSetter);
	}

	ActionResult onBiomePlace(float temperature, float humidity, Consumer<Biome> biomeSetter);
}
