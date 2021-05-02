package io.github.minecraftcursedlegacy.impl.command;

import io.github.minecraftcursedlegacy.accessor.command.AccessorMinecraft;
import io.github.minecraftcursedlegacy.api.command.Sender;
import net.minecraft.entity.player.Player;

public class PlayerSender implements Sender {
	private final Player player;

	public PlayerSender(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return this.player;
	}

	public String getName() {
		return this.player.name;
	}

	public void sendCommandFeedback(String message) {
		AccessorMinecraft.getInstance().overlay.addChatMessage(message);
	}

	public void sendError(String message) {
		sendCommandFeedback("§4"+message);
	}
}
