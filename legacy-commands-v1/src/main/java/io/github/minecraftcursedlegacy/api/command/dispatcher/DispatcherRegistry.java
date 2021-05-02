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

package io.github.minecraftcursedlegacy.api.command.dispatcher;

import java.util.HashMap;
import java.util.Map;

import io.github.minecraftcursedlegacy.api.command.Sender;

/**
 * Registry for for mapping commands to the relevant {@linkplain CommandDispatcher command dispatchers}.
 * @since 1.1.0
 */
public class DispatcherRegistry {
	private static final Map<String, CommandDispatcher> SINGLEPLAYER_COMMANDS = new HashMap<>();
	private static final Map<String, CommandDispatcher> MULTIPLAYER_COMMANDS = new HashMap<>();
	private static boolean singleplayerChat = false;

	/**
	 * Set the dispatcher associated with this command on singleplayer.
	 * @param command the command to set the dispatcher for.
	 * @param dispatcher the dispatcher in charge of handling and dispatching this command.
	 */
	public static void registerSingleplayerDispatcher(String command, CommandDispatcher dispatcher) {
		SINGLEPLAYER_COMMANDS.put(command, dispatcher);
		enableSingleplayerChat();
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
	 * Sets the chat box in singleplayer to be enabled.
	 * Called automatically when a singleplayer command is registered.
	 */
	public static void enableSingleplayerChat() {
		singleplayerChat = true;
	}

	/**
	 * @return whether the chat console functionality should be enabled in singleplayer. Used by the implementation.
	 */
	public static boolean isSingleplayerChatEnabled() {
		return singleplayerChat;
	}

	/**
	 * Called when a command is issued to the game by a player. This method finds the relevant command dispatcher and delegates issuing the command to it.
	 * @param sender an object containg info and utility methods for the issuer of the command.
	 * @param commandName the name of the command that has been issued.
	 * @param command the command string, excluding the "/" (slash) at the beginning if executed from chat.
	 * @param singleplayer whether this is executed in a singleplayer world.
	 * @return whether the command was handled.
	 */
	public static boolean dispatch(Sender sender, String commandName, String command, boolean singleplayer) {
		CommandDispatcher dispatcher = (singleplayer ? SINGLEPLAYER_COMMANDS : MULTIPLAYER_COMMANDS).get(commandName);

		if (dispatcher != null) {
			return dispatcher.dispatch(sender, commandName, command, singleplayer);
		}

		return false;
	}
}