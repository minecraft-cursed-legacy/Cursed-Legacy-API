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

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

/**
 * Event for when a command is issued on the server. Used by the command implementation for multiplayer commands.
 * @since 1.1.0
 */
public interface CommandDispatchEvent {
	Event<CommandDispatchEvent> INSTANCE = EventFactory.createArrayBacked(CommandDispatchEvent.class, listeners -> (sender, command) -> {
		for (CommandDispatchEvent listener : listeners) {
			if (listener.onDispatch	(sender, command)) {
				return true;
			}
		}

		return false;
	});

	/**
	 * Called when a command is issued by a source.
	 * @param sender the source who has issued the command.
	 * @param command the command string, excluding the "/" (slash) at the beginning if executed from chat.
	 *
	 * @return whether the command was handled. 
	 */
	boolean onDispatch(Sender sender, String command);
}
