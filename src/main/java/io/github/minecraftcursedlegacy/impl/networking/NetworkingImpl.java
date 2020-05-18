package io.github.minecraftcursedlegacy.impl.networking;

import io.github.minecraftcursedlegacy.accessor.AccessorAbstractPacket;
import net.fabricmc.api.ModInitializer;

public class NetworkingImpl implements ModInitializer {
	@Override
	public void onInitialize() {
		AccessorAbstractPacket.register(250, true, true, PluginMessagePacket.class);
	}
}