package io.github.minecraftcursedlegacy.api.networking;

import io.github.minecraftcursedlegacy.api.registry.Id;
import io.github.minecraftcursedlegacy.impl.networking.PluginMessagePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.ServerPlayer;
import net.minecraft.network.PacketHandler;

public abstract class PluginChannel {
	/**
	 * Returns the unique channel identifier of this channel in the form of modid:channelname
	 * This should always have the same return value.
	 * @return Channel Identifier
	 */
	public abstract Id getChannelIdentifier();
	
	/**
	 * Called when this channel recieves a packet.
	 * @param arg The local PacketHandler
	 * @param data The data of the packet
	 */
	public abstract void onRecieve(PacketHandler arg, byte[] data);
	
	/**
	 * Server packet send method.
	 * @param data The data to send
	 * @param player The player to send the data to
	 */
	public void send(byte[] data, ServerPlayer player) {
		player.field_255.method_835(new PluginMessagePacket(getChannelIdentifier().toString(), data));
	}

	/**
	 * Client packet send method.
	 * @param data The data to send
	 * @param mc The local Minecraft
	 */
	public void send(byte[] data, Minecraft mc) {
		mc.method_2145().sendPacket(new PluginMessagePacket(getChannelIdentifier().toString(), data));
	}

}