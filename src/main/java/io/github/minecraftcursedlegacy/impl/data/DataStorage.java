package io.github.minecraftcursedlegacy.impl.data;

import java.util.function.Supplier;

import io.github.minecraftcursedlegacy.api.data.ModdedData;
import io.github.minecraftcursedlegacy.api.registry.Id;
import net.minecraft.util.io.CompoundTag;

public interface DataStorage {
	CompoundTag getModdedTag();
	CompoundTag setModdedTag(CompoundTag tag);
	ModdedData getModdedData(Id id, Supplier<ModdedData> supplier);
}
