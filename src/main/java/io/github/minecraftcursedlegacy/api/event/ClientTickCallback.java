package io.github.minecraftcursedlegacy.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.Minecraft;

/**
 * Callback for ticks on the client.
 */
@FunctionalInterface
public interface ClientTickCallback {
	Event<ClientTickCallback> EVENT = EventFactory.createArrayBacked(ClientTickCallback.class,
			listeners -> client -> {
				for (ClientTickCallback listener : listeners) {
					listener.onClientTick(client);
				}
			});

	/**
	 * Called when the client ticks.
	 * @param client the minecraft client instance.
	 */
	void onClientTick(Minecraft client);
}
