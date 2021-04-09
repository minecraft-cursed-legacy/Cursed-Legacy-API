package io.github.minecraftcursedlegacy.api.event.lifecycle;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.MinecraftServer;

/**
 * Callback for ticks on the dedicated server. Does *not* run in singleplayer!
 */
@FunctionalInterface
public interface DedicatedServerTickCallback {
	Event<DedicatedServerTickCallback> EVENT = EventFactory.createArrayBacked(DedicatedServerTickCallback.class,
			listeners -> server -> {
				for (DedicatedServerTickCallback listener : listeners) {
					listener.onServerTick(server);
				}
			});

	/**
	 * Called when the dedicated server ticks.
	 * @param server the dedicated server instance.
	 */
	void onServerTick(MinecraftServer server);
}
