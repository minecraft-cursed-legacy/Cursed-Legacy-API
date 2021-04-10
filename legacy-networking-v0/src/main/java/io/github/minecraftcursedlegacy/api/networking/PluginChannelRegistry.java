package io.github.minecraftcursedlegacy.api.networking;

import io.github.minecraftcursedlegacy.impl.networking.PluginChannelRegistryImpl;

public class PluginChannelRegistry {
	private PluginChannelRegistry() {
	}

	public static void registerPluginChannel(PluginChannel channel) {
		PluginChannelRegistryImpl.registerPluginChannel(channel);
	}
}