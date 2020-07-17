package io.github.minecraftcursedlegacy.impl.data;

import java.util.function.Supplier;

import io.github.minecraftcursedlegacy.api.data.AttachedData;
import io.github.minecraftcursedlegacy.api.registry.Id;
import net.minecraft.util.io.CompoundTag;

public interface DataStorage {
	CompoundTag getModdedTag();
	void putAttachedData(Id id, AttachedData data);
	AttachedData getAttachedData(Id id, Supplier<AttachedData> supplier);
}
