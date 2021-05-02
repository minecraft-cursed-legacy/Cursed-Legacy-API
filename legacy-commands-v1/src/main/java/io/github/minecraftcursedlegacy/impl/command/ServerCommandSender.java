package io.github.minecraftcursedlegacy.impl.command;

import io.github.minecraftcursedlegacy.accessor.command.AccessorServerPlayPacketHandler;
import io.github.minecraftcursedlegacy.api.command.Sender;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.Player;
import net.minecraft.server.command.CommandSource;

@Environment(EnvType.SERVER)
public class ServerCommandSender implements Sender {
	private final CommandSource source;

	public ServerCommandSender(CommandSource source) {
		this.source = source;
	}

	public Player getPlayer() {
		if (source instanceof AccessorServerPlayPacketHandler) {
			return ((AccessorServerPlayPacketHandler) source).getPlayer();
		}
		return null;
	}

	public String getName() {
		return this.source.getName();
	}

	public void sendCommandFeedback(String message) {
		this.source.sendFeedback(message);
	}

	public void sendError(String message) {
		this.source.sendFeedback("§4"+message);
	}
}
