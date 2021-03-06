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

import io.github.minecraftcursedlegacy.api.event.ActionResult;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

/**
 * Event for when a chat message is sent. Used by the command implementation, but also has uses in chat filtering, etc.
 * @since 1.1.0
 */
public interface ChatEvent {
	/**
	 * Invoked when a chat message is sent in a singleplayer world.
	 */
	Event<ChatEvent> SINGLEPLAYER = EventFactory.createArrayBacked(ChatEvent.class, (listeners -> (sender, message) -> {
		for (ChatEvent listener : listeners) {
			ActionResult result = listener.onMessageSent(sender, message);

			if (result != ActionResult.PASS) {
				return result;
			}
		}

		return ActionResult.PASS;
	}));

	/**
	 * Invoked when a chat message is sent in a multiplayer world, handled server side.
	 */
	Event<ChatEvent> MULTIPLAYER = EventFactory.createArrayBacked(ChatEvent.class, (listeners -> (sender, message) -> {
		for (ChatEvent listener : listeners) {
			ActionResult result = listener.onMessageSent(sender, message);

			if (result != ActionResult.PASS) {
				return result;
			}
		}

		return ActionResult.PASS;
	}));

	/**
	 * Invoked client side by the sending player when a chat message is sent in a multiplayer world.
	 * This has more limited use cases than the other events, but may be useful if you want to have configuration commands for a client side mod while on the server.
	 */
	Event<ChatEvent> MULTIPLAYER_CLIENT = EventFactory.createArrayBacked(ChatEvent.class, (listeners -> (sender, message) -> {
		for (ChatEvent listener : listeners) {
			ActionResult result = listener.onMessageSent(sender, message);

			if (result != ActionResult.PASS) {
				return result;
			}
		}

		return ActionResult.PASS;
	}));

	/**
	 * Called when a command is issued by a source.
	 * @param sender the source who has issued the command.
	 * @param message the chat message.
	 *
	 * @return
	 * <ul>
	 *   <li>SUCCESS</li> to prevent further event handling and immediately send the chat message.
	 *   <li>FAIL</li> to prevent further event handling and do not send the chat message.
	 *   <li>PASS</li> to continue further event handling. If all events pass, the chat message will successfully send.
	 */
	ActionResult onMessageSent(Sender sender, String message);
}
