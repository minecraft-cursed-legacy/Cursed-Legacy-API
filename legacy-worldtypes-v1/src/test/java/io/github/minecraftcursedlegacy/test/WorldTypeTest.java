package io.github.minecraftcursedlegacy.test;

import net.fabricmc.api.ModInitializer;

public class WorldTypeTest implements ModInitializer {
	@Override
	public void onInitialize() {
		new RandomWorldType();
		new LowFlatWorldType();
	}
}
