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

package io.github.minecraftcursedlegacy.api.event.lifecycle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.Minecraft;

/**
 * Lifecycle events for the client.
 * @since 1.1.0
 */
@Environment(EnvType.CLIENT)
public class ClientLifecycleEvents {
	/**
	 * Event for the start of the client tick.
	 */
	public static final Event<Tick> START_TICK = EventFactory.createArrayBacked(Tick.class,
			listeners -> client -> {
				for (Tick listener : listeners) {
					listener.onClientTick(client);
				}
			});

	/**
	 * Event for the end of the client tick.
	 */
	public static final Event<Tick> END_TICK = EventFactory.createArrayBacked(Tick.class,
			listeners -> client -> {
				for (Tick listener : listeners) {
					listener.onClientTick(client);
				}
			});

	@FunctionalInterface
	public interface Tick {
		/**
		 * Called when the client ticks.
		 * @param client the minecraft client instance.
		 */
		void onClientTick(Minecraft client);
	}
}
