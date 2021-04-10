package io.github.minecraftcursedlegacy.api.attacheddata.v1;

import io.github.minecraftcursedlegacy.api.registry.Id;
import net.minecraft.util.io.CompoundTag;

/**
 * Data which can be attached to various vanilla objects, such as items and levels.
 * @see {@link DataManager}.
 * @since 1.0.0
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
