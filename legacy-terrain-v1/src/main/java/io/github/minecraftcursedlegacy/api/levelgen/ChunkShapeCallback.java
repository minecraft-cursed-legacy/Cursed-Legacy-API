package io.github.minecraftcursedlegacy.api.levelgen;

import io.github.minecraftcursedlegacy.api.terrain.ChunkGenEvents;

/**
 * Callback for chunk shaping.
 */
@FunctionalInterface
@Deprecated
public interface ChunkShapeCallback extends ChunkGenEvents.Shape {
}
