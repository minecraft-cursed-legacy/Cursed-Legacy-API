package io.github.minecraftcursedlegacy.impl.command;

import io.github.minecraftcursedlegacy.accessor.command.AccessorMinecraft;
import io.github.minecraftcursedlegacy.api.command.Sender;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.Player;

@Environment(EnvType.CLIENT)
public class PlayerSender implements Sender {
	Player player = null;

	public PlayerSender(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	public String getName() {
		return player.name;
	}

	public void sendCommandFeedback(String message) {
		AccessorMinecraft.getInstance().overlay.addChatMessage(message);
	}

	public void sendError(String message) {
		sendCommandFeedback("§4"+message);
	}
}
