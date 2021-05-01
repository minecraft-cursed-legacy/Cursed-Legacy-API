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
import net.fabricmc.api.EnvType;

import javax.annotation.Nullable;

/**
 * Interface to the default command dispatcher in cursed legacy API.
 * Yes, this is where you register your commands to.
 * Access the instance from {@code CommandDispatcher.DEFAULT}.
 */
public interface DefaultCommandDispatcher {

	DefaultCommandDispatcher INSTANCE = new DefaultCommandDispatcherImpl();

	/**
	 * Register a command to the command dispatcher.
	 * @param command the command name.
	 * @param handler the object in charge of executing the command.
	 */
	default void register(String command, Command handler) {
		register(command, handler, null);
	}

	/**
	 * Register a command to the command dispatcher.
	 * @param command the command name.
	 * @param handler the object in charge of executing the command.
	 * @param environment the play environment in which to register the command:
	 * <ul>
	 *   <li>CLIENT means it will exist only on singleplayer
	 *   <li>SERVER means it will exist only on multiplayer
	 *   <li>null means it will register for both singleplayer and multiplayer.
	 * <ul>
	 */
	void register(String command, Command handler, @Nullable EnvType environment);

	/**
	 * A simple command for use with the default command dispatcher.
	 */
	@FunctionalInterface
	public interface Command {
		/**
		 * Called on command execution.
		 * @param source the source executing the command. Will be null if it is the console.
		 * @param args the command arguments. {@code args[0]} will always be the command name.
		 * @return whether the command executed successfully.
		 */
		boolean execute(CursedCommandSource source, String[] args);
	}
}
