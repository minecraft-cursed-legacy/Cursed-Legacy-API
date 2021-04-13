package io.github.minecraftcursedlegacy.api.attacheddata.v1;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Supplier;

import io.github.minecraftcursedlegacy.api.registry.Id;
import net.minecraft.util.io.CompoundTag;

/**
 * Simple {@linkplain DataStorage} which only requires one method to be implemented for the others to be automatically done.
 * @since 1.0.0
 */
public interface SimpleDataStorage extends DataStorage {
	@Override
	default CompoundTag getModdedTag() {
		CompoundTag tag = new CompoundTag();

		this.getRawAttachedDataMap().forEach((id, data) -> {
			tag.put(id.toString(), data.toTag(new CompoundTag()));
		});

		return tag;
	}

	@Override
	default Set<Entry<Id,AttachedData>> getAllAttachedData() {
		return this.getRawAttachedDataMap().entrySet();
	}

	@Override
	default void putAttachedData(Id id, AttachedData data) {
		this.getRawAttachedDataMap().put(id, data);
	}

	@Override
	default AttachedData getAttachedData(Id id, Supplier<AttachedData> supplier) {
		return this.getRawAttachedDataMap().computeIfAbsent(id, i -> supplier.get());
	}

	/**
	 * @return the raw map of ids to attached data that underlies this {@link SimpleDataStorage}.
	 */
	Map<Id, AttachedData> getRawAttachedDataMap();
}
