package io.github.minecraftcursedlegacy.api.levelgen;

import io.github.minecraftcursedlegacy.api.terrain.ChunkGenEvents;

/**
 * Callback for chunk decoration.
 * @deprecated use {@linkplain ChunkGenEvents.Decorate} instead.
 */
@FunctionalInterface
@Deprecated
public interface ChunkDecorateCallback extends ChunkGenEvents.Decorate {
}
