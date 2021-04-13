package io.github.minecraftcursedlegacy.api.attacheddata.v1;

import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Supplier;

import io.github.minecraftcursedlegacy.api.registry.Id;
import net.minecraft.util.io.CompoundTag;

/**
 * Interface implemented by mixins onto game objects that specifies useful methods for the storage of persistent attached data.
 * @since 1.0.0
 * @apiNote this class has existed ever since the attached data code was added to cursed legacy api. However, it was an implementation-only class
 * until it was officially made API in 1.0.0.
 */
public interface DataStorage {
	/**
	 * @return an NBT tag which contains the modded data of the object.
	 */
	CompoundTag getModdedTag();
	/**
	 * Puts the attached data on the object.
	 * @param id the id of the attached data
	 * @param data the data to attach.
	 */
	void putAttachedData(Id id, AttachedData data);
	/**
	 * Retrieves the attached data for the id or attaches the default via a given supplier if it does not exist.
	 * @param id the id of the attached data.
	 * @param supplier the supplier which constructs a new attached data object if it does not exist already.
	 * This supplier should be similar or equal to the one used in the built in {@linkplain DataManager}.
	 * @return the data attached to the object.
	 */
	AttachedData getAttachedData(Id id, Supplier<AttachedData> supplier);
	/**
	 * Retrieves the entry set of all attached data on the object.
	 */
	Set<Entry<Id,AttachedData>> getAllAttachedData();
}
