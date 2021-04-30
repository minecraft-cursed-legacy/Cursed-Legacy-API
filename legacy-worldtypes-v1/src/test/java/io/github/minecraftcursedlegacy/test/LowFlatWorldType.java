package io.github.minecraftcursedlegacy.test;

import io.github.minecraftcursedlegacy.api.registry.Id;
import io.github.minecraftcursedlegacy.api.registry.Translations;
import io.github.minecraftcursedlegacy.api.worldtype.WorldType;
import net.minecraft.level.Level;
import net.minecraft.level.source.LevelSource;
import net.minecraft.util.io.CompoundTag;

public class LowFlatWorldType extends WorldType {
	public LowFlatWorldType() {
		super(new Id("modid", "lowflat"));
		Translations.addTranslation(this.toString(), "Low Flat");
	}

	@Override
	public LevelSource createChunkGenerator(Level level, CompoundTag additionalData) {
		return new LowFlatChunkGenerator(level, level.getSeed());
	}
}
