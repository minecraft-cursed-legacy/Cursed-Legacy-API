package io.github.minecraftcursedlegacy.networking.impl;

import io.github.minecraftcursedlegacy.networking.accessor.AccessorAbstractPacket;
import net.fabricmc.api.ModInitializer;

public class NetworkingImpl implements ModInitializer {
	@Override
	public void onInitialize() {
		AccessorAbstractPacket.register(250, true, true, PluginMessagePacket.class);
	}
}