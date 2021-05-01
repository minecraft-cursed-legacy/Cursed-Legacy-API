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

package io.github.minecraftcursedlegacy.api.command;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import net.minecraft.entity.player.Player;

/**
 * Registry for for mapping commands to the relevant {@linkplain CommandDispatcher command dispatchers}.
 * @since 1.1.0
 */
public class DispatcherRegistry {
	private static final Map<String, CommandDispatcher> SINGLEPLAYER_COMMANDS = new HashMap<>();
	private static final Map<String, CommandDispatcher> MULTIPLAYER_COMMANDS = new HashMap<>();

	/**
	 * Set the dispatcher associated with this command on singleplayer.
	 * @param command the command to set the dispatcher for.
	 * @param dispatcher the dispatcher in charge of handling and dispatching this command.
	 */
	public static void registerSingleplayerDispatcher(String command, CommandDispatcher dispatcher) {
		SINGLEPLAYER_COMMANDS.put(command, dispatcher);
	}

	/**
	 * Set the dispatcher associated with this command on multiplayer.
	 * @param command the command to set the dispatcher for.
	 * @param dispatcher the dispatcher in charge of handling and dispatching this command.
	 */
	public static void registerMultiplayerDispatcher(String command, CommandDispatcher dispatcher) {
		MULTIPLAYER_COMMANDS.put(command, dispatcher);
	}

	/**
	 * @return whether any singleplayer commands have been registered with an accompanying dispatcher. Used by the implementation.
	 */
	public static boolean hasSingleplayerCommands() {
		return !SINGLEPLAYER_COMMANDS.isEmpty();
	}

	/**
	 * Called when a command is issued to the game by a player. This method finds the relevant command dispatcher and delegates issuing the command to it.
	 * @param player the player who has issued the command. Will be null if it is the console.
	 * @param commandName the name of the command that has been issued.
	 * @param command the command string, excluding the "/" (slash) at the beginning if executed from chat.
	 * @param singleplayer whether this is executed in a singleplayer world.
	 */
	public static void dispatch(@Nullable Player player, String commandName, String command, boolean singleplayer) {
		CommandDispatcher dispatcher = (singleplayer ? SINGLEPLAYER_COMMANDS : MULTIPLAYER_COMMANDS).get(commandName);

		if (dispatcher != null) {
			dispatcher.dispatch(player, commandName, command, singleplayer);
		}
	}
}
