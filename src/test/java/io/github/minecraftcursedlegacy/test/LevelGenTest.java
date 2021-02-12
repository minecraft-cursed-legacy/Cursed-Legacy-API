package io.github.minecraftcursedlegacy.test;

import io.github.minecraftcursedlegacy.api.event.ActionResult;
import io.github.minecraftcursedlegacy.api.levelgen.BiomePlacementCallback;
import net.fabricmc.api.ModInitializer;
import net.minecraft.level.biome.Biome;

public class LevelGenTest implements ModInitializer {
	@Override
	public void onInitialize() {
		biome = new TestBiome("Test");

		BiomePlacementCallback.EVENT.register((temperature, humidity, biomesetter) -> {
			float t = temperature;

			while (t > 0.05f) {
				t -= 0.05f;
			}

			if (t > 0.025f) {
				biomesetter.accept(biome);
				return ActionResult.SUCCESS;
			}

			return ActionResult.PASS;
		});
	}

	public static Biome biome;
}
