package io.github.minecraftcursedlegacy.api.data;

import io.github.minecraftcursedlegacy.api.registry.Id;
import net.minecraft.util.io.CompoundTag;

/**
 * Data which can be attached to various vanilla objects, such as items and blocks.
 * @see {@link ModdedDataManager}.
 */
public interface ModdedData {
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
	 * Creates a deep copy of this {@link ModdedData}, similar to the recommendations for {@link Object#clone}.
	 */
	ModdedData copy();
}
