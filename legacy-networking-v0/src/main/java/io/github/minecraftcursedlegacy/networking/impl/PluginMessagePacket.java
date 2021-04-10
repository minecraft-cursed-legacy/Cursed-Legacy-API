package io.github.minecraftcursedlegacy.networking.impl;

import net.minecraft.network.PacketHandler;
import net.minecraft.packet.AbstractPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

//Similar to Plugin Message but supports 
public class PluginMessagePacket extends AbstractPacket {
	public String channel;
	public byte[] data;

	public PluginMessagePacket() {
	}

	public PluginMessagePacket(String channel, byte[] data) {
		this.channel = channel;
		this.data = data;
	}

	@Override
	public void read(DataInputStream dataInputStream) {
		try {
			short channellength = dataInputStream.readShort();
			// Vanilla code is complicated so just duplicate logic here
			StringBuilder buffer = new StringBuilder();
			for (int i = 0; i < channellength; i++) {
				buffer.append(dataInputStream.readChar());
			}

			channel = buffer.toString();

			int datalength = dataInputStream.readInt();
			data = new byte[datalength];
			dataInputStream.read(data, 0, datalength);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void write(DataOutputStream dataOutputStream) {
		try {
			dataOutputStream.writeShort(channel.length());
			dataOutputStream.writeChars(channel);
			dataOutputStream.writeInt(data.length);
			dataOutputStream.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handle(PacketHandler arg) {
		PluginChannelRegistryImpl.handlePacket(arg, this);
	}

	@Override
	public int length() {
		return 2 /* short length */ + channel.length() + 2 /* short length */ + data.length;
	}
}