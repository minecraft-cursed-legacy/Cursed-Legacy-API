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

import javax.annotation.Nullable;

import io.github.minecraftcursedlegacy.impl.command.PlayerSender;
import net.minecraft.entity.player.Player;

/**
 * Information about and utility methods for handling incoming commands and chat messages.
 * @since 1.1.0
 */
public interface Sender {
	/**
	 * @return the player executing the command. Will be null if it is the console.
	 */
	@Nullable Player getPlayer();
	/**
	 * Sends a chat message to the command source as feedback.
	 * @param message the message to send. 
	 */
	void sendCommandFeedback(String message);
	/**
	 * Sends an error chat message to the command source as feedback.
	 * @param message the message to send.
	 */
	void sendError(String message);

	/**
	 * Creates a new sender from the given player.
	 * @param player the player who is responsible for sending.
	 * @return the new sender instance.
	 */
	static Sender fromPlayer(Player player) {
		return new PlayerSender(player);
	}
}
