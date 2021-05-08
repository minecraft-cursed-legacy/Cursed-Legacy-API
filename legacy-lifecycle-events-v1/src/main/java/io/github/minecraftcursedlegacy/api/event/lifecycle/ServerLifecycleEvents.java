/*
 * Copyright (c) 2020 The Cursed Legacy Team.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package io.github.minecraftcursedlegacy.api.event.lifecycle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.player.ServerPlayer;

/**
 * Lifecycle events for the server.
 * @since 1.1.0
 * @apiNote Remember: None of these events run in singleplayer!
 */
@Environment(EnvType.SERVER)
public class ServerLifecycleEvents {
	/**
	 * Event for the start of the dedicated server tick.
	 */
	public static final Event<Tick> START_TICK = EventFactory.createArrayBacked(Tick.class,
			listeners -> server -> {
				for (Tick listener : listeners) {
					listener.onServerTick(server);
				}
			});

	/**
	 * Event for the end of the dedicated server tick.
	 */
	public static final Event<Tick> END_TICK = EventFactory.createArrayBacked(Tick.class,
			listeners -> server -> {
				for (Tick listener : listeners) {
					listener.onServerTick(server);
				}
			});

	/**
	 * Event for a player logging in to the server.
	 */
	public static final Event<PlayerLogin> PLAYER_LOGIN = EventFactory.createArrayBacked(PlayerLogin.class,
			listeners -> player -> {
				for (PlayerLogin listener : listeners) {
					listener.onPlayerLogin(player);

					// In case a mod's listener has disconnected the player for some reason
					// This is the equivalent of cancelling the login, but with vanilla functionality handling it.
					if (player.packetHandler.field_918) {
						break;
					}
				}
			});

	/**
	 * Event for a player being disconnected / disconnecting from the server
	 */
	public static final Event<PlayerDisconnect> PLAYER_DISCONNECT = EventFactory.createArrayBacked(PlayerDisconnect.class,
			listeners -> player -> {
				for (PlayerDisconnect listener : listeners) {
					listener.onPlayerDisconnect(player);
				}
			});

	@FunctionalInterface
	public interface Tick {
		/**
		 * Called when the dedicated server ticks.
		 * @param server the dedicated server instance.
		 */
		void onServerTick(MinecraftServer server);
	}

	@FunctionalInterface
	public interface PlayerLogin {
		/**
		 * Called when a player successfully logs in to the server.
		 * @param player the player that has just logged in.
		 */
		void onPlayerLogin(ServerPlayer player);
	}

	@FunctionalInterface
	public interface PlayerDisconnect {
		/**
		 * Called when a player disconnects from the server.
		 * @param player the player that has disconnected.
		 */
		void onPlayerDisconnect(ServerPlayer player);
	}
}
