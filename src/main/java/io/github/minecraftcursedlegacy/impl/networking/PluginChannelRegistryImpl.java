package io.github.minecraftcursedlegacy.impl.networking;

import java.util.HashMap;

import io.github.minecraftcursedlegacy.api.networking.PluginChannel;
import net.minecraft.network.PacketHandler;

public class PluginChannelRegistryImpl {
	private PluginChannelRegistryImpl() {
	}

	private static HashMap<String, PluginChannel> channelMap = new HashMap<>();
	
	public static void registerPluginChannel(PluginChannel channel) {
		channelMap.put(channel.getChannelIdentifier().toString(), channel);
	}

	protected static void handlePacket(PacketHandler arg, PluginMessagePacket packet) {
		channelMap.get(packet.channel).onRecieve(arg, packet.data);
	}
}