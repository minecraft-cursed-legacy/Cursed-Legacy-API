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

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import io.github.minecraftcursedlegacy.api.command.DefaultCommandDispatcher;
import io.github.minecraftcursedlegacy.api.command.DispatcherRegistry;
import io.github.minecraftcursedlegacy.api.command.Sender;
import net.fabricmc.api.EnvType;

/**
 * The implementation for the default command dispatcher.
 * @since 1.1.0
 */
public class DefaultCommandDispatcherImpl implements DefaultCommandDispatcher{
	private final Map<String, Command> singleplayerCommands = new HashMap<>();
	private final Map<String, Command> multiplayerCommands = new HashMap<>();

	public void register(String commandName, Command commandFunction, @Nullable EnvType environment) {
		if (environment != EnvType.CLIENT) {
			multiplayerCommands.put(commandName, commandFunction);
			DispatcherRegistry.registerMultiplayerDispatcher(commandName, this);
		}

		if (environment != EnvType.SERVER) {
			singleplayerCommands.put(commandName, commandFunction);
			DispatcherRegistry.registerSingleplayerDispatcher(commandName, this);
		}
	}

	@Override
	public boolean dispatch(Sender sender, String commandName, String command, boolean singleplayer) {
		String[] args = command.split(" ");

		try {
			if (!(singleplayer ? this.singleplayerCommands : this.multiplayerCommands).get(commandName).execute(sender, args)) {
				sender.sendError("An unexpected issue occured while processing this command.");
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			sender.sendError("An unexpected error occured while processing this command.");
		}

		return true;
	}
}
