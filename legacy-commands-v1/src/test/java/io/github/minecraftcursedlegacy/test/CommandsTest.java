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

package io.github.minecraftcursedlegacy.test;

import io.github.minecraftcursedlegacy.api.command.ChatEvent;
import io.github.minecraftcursedlegacy.api.command.dispatcher.CommandDispatcher;
import io.github.minecraftcursedlegacy.api.event.ActionResult;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;

public class CommandsTest implements ModInitializer {
	@Override
	public void onInitialize() {
		// command
		CommandDispatcher.DEFAULT.register("echo", (source, args) -> {
			source.sendCommandFeedback(args[1]);
			return true;
		});
		
		CommandDispatcher.DEFAULT.register("singleplayerecho", (source, args) -> {
			source.sendCommandFeedback(args[1]);
			return true;
		}, EnvType.CLIENT);
		
		// because I can
		ChatEvent.SINGLEPLAYER.register((sender, msg) -> {
			if (!msg.startsWith("/")) {
				sender.sendCommandFeedback("<" + sender.getPlayer().name + "'s Second Cousin> " + msg);
				return ActionResult.SUCCESS;
			}
			
			return ActionResult.PASS;
		});

		// test the event that I don't use in implementation
		ChatEvent.MULTIPLAYER.register((sender, msg) -> {
			if (msg.toLowerCase().contains("fuck")) {
				sender.sendCommandFeedback("That message contains a bad bad no-no word!");
				return ActionResult.FAIL;
			}
			
			return ActionResult.PASS;
		});
	}
}
