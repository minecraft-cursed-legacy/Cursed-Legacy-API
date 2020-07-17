package io.github.minecraftcursedlegacy.impl.data;

import java.util.function.Supplier;

import io.github.minecraftcursedlegacy.api.data.AttachedData;
import io.github.minecraftcursedlegacy.api.registry.Id;
import net.minecraft.util.io.CompoundTag;

public interface DataStorage {
	CompoundTag getModdedTag();
	void putModdedData(Id id, AttachedData data);
	AttachedData getModdedData(Id id, Supplier<AttachedData> supplier);
}
