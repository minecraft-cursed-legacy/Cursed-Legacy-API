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

import io.github.minecraftcursedlegacy.impl.command.DefaultCommandDispatcherImpl;

/**
 * Interface for an object in charge of registering and dispatching commands issued to the game.
 * @since 1.1.0
 */
public interface CommandDispatcher {
	/**
	 * The default command dispatcher, as provided by Cursed Legacy API.
	 */
	DefaultCommandDispatcher DEFAULT = new DefaultCommandDispatcherImpl();

	/**
	 * Called when a command is issued to the game. This method is used to handle command dispatching
	 * logic, sending the data to the relevant registered command object.
	 * @param sender the sender who has issued the command.e.
	 * @param commandName the name of the command that has been issued.
	 * @param command the command string, excluding the "/" (slash) at the beginning if executed from chat.
	 * @param singleplayer whether this is executed in a singleplayer world.
	 * @return whether the command was handled.
	 */
	boolean dispatch(Sender sender, String commandName, String command, boolean singleplayer);
}