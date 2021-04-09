package io.github.minecraftcursedlegacy.api.data;

import io.github.minecraftcursedlegacy.api.registry.Id;
import net.minecraft.util.io.CompoundTag;

/**
 * Data which can be attached to various vanilla objects, such as items and blocks.
 * @see {@link DataManager}.
 */
public interface AttachedData {
	/**
	 * @return the id of this modded data.
	 */
	Id getId();
	/**
	 * @return a tag representation of this data.
	 */
	CompoundTag toTag(CompoundTag tag);
	/**
	 * @param tag the tag from which to load data.
	 */
	void fromTag(CompoundTag tag);
	/**
	 * Creates a deep copy of this {@link AttachedData}, similar to the recommendations for {@link Object#clone}.
	 */
	AttachedData copy();
}
