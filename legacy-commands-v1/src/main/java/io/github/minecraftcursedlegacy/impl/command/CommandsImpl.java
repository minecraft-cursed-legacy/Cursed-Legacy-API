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

package io.github.minecraftcursedlegacy.impl.command;

import io.github.minecraftcursedlegacy.api.command.ChatEvent;
import io.github.minecraftcursedlegacy.api.command.CommandDispatchEvent;
import io.github.minecraftcursedlegacy.api.command.dispatcher.DispatcherRegistry;
import io.github.minecraftcursedlegacy.api.event.ActionResult;
import net.fabricmc.api.ModInitializer;

/** 
 * Implementation for command dispatchers that use our api.
 * @since 1.1.0
 */
public class CommandsImpl implements ModInitializer {
	@Override
	public void onInitialize() {
		// Client
		ChatEvent.SINGLEPLAYER.register((sender, message) -> {
			// isClient is only true when a client connected to the server
			if (message.length() > 1 && message.startsWith("/") && !sender.getPlayer().level.isClient) {
				message = message.substring(1);
				String[] args = message.split(" ", 2);

				if (DispatcherRegistry.dispatch(sender, args[0], message, true)) {
					return ActionResult.FAIL;
				}
			}

			return ActionResult.PASS;
		});

		// Server
		CommandDispatchEvent.INSTANCE.register((sender, command) -> {
			// isClient is only true when a client connected to the server
			String[] args = command.split(" ", 2);

			if (DispatcherRegistry.dispatch(sender, args[0], command, true)) {
				return true;
			}

			return false;
		});
	}
}
