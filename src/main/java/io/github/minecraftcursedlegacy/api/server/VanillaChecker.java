package io.github.minecraftcursedlegacy.api.server;

import io.github.minecraftcursedlegacy.impl.server.VanillaCheckerImpl;
import net.minecraft.entity.player.Player;

public final class VanillaChecker {
	private VanillaChecker(){/*No Constructor*/}

	/**
	 * Checks if a player is using a Vanilla Client
	 * @param player
	 * @return
	 */
	public static boolean isVanilla(Player player) {
		return VanillaCheckerImpl.playermap.get(player.name);
	}
}