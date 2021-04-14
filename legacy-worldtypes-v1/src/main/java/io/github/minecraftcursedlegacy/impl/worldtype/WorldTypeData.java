package io.github.minecraftcursedlegacy.impl.worldtype;

import javax.annotation.Nullable;

import io.github.minecraftcursedlegacy.api.attacheddata.v1.AttachedData;
import io.github.minecraftcursedlegacy.api.registry.Id;
import net.minecraft.util.io.CompoundTag;

/**
 * Attached data for world types.
 * @since 1.0.0
 */
public class WorldTypeData implements AttachedData {
	WorldTypeData(Id typeId) {
		this.typeId = typeId;
	}

	private Id typeId;
	@Nullable
	private CompoundTag additionalData;

	/**
	 * Set the world type id in this data.
	 */
	public void setTypeId(Id id) {
		this.typeId = id;
	}

	@Nullable
	public CompoundTag getOrCreateLoadedData(boolean storesAdditionalData) {
		// If null and should not be null, set it to a new blank compound tag. Otherwise, just return the value (which will be null if it doesn't store data, or the existing tag)
		return this.additionalData == null && storesAdditionalData ? (this.additionalData = new CompoundTag()) : this.additionalData;
	}

	/**
	 * @return the world type id in this data.
	 */
	public Id getTypeId() {
		return this.typeId;
	}

	@Override
	public Id getId() {
		return ID;
	}

	@Override
	public CompoundTag toTag(CompoundTag tag) {
		tag.put("id", this.typeId.toString());

		if (this.additionalData != null) {
			tag.put("data", this.typeId.toString());
		}

		return tag;
	}

	@Override
	public void fromTag(CompoundTag tag) {
		this.typeId = new Id(tag.getString("id"));

		if (tag.containsKey("data")) {
			this.additionalData = tag.getCompoundTag("data");
		}
	}

	@Override
	public AttachedData copy() {
		return new WorldTypeData(this.typeId);
	}

	public static final Id ID = new Id("api", "world_type");
}
