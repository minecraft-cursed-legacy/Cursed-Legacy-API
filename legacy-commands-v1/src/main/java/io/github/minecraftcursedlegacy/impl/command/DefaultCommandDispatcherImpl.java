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

import io.github.minecraftcursedlegacy.api.command.CommandDispatchEvent;
import io.github.minecraftcursedlegacy.api.command.DefaultCommandDispatcher;
import net.fabricmc.api.EnvType;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class DefaultCommandDispatcherImpl implements DefaultCommandDispatcher{
	private final Map<String, Command> singleplayerCommands = new HashMap<>();
	private final Map<String, Command> multiplayerCommands = new HashMap<>();

	public DefaultCommandDispatcherImpl() {
		CommandDispatchEvent.MULTIPLAYER_DISPATCH.register((source, commandString) -> {
			String[] args = commandString.split(" ");
			Command command = multiplayerCommands.get(args[0]);
			if (command != null) {
				command.execute(source, args);
				return true;
			}
			return false;
		});

		CommandDispatchEvent.SINGLEPLAYER_DISPATCH.register((source, commandString) -> {
			String[] args = commandString.split(" ");
			Command command = singleplayerCommands.get(args[0]);
			if (command != null) {
				command.execute(source, args);
				return true;
			}
			return false;
		});
	}

	public void register(String commandName, Command commandFunction, @Nullable EnvType environment) {
		if (environment == EnvType.SERVER) {
			multiplayerCommands.put(commandName, commandFunction);
		} else if (environment == EnvType.CLIENT) {
			singleplayerCommands.put(commandName, commandFunction);
		} else {
			singleplayerCommands.put(commandName, commandFunction);
			multiplayerCommands.put(commandName, commandFunction);
		}
	}
}
