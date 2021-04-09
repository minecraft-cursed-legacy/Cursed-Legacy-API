package io.github.minecraftcursedlegacy.registry.accessor;

import java.io.File;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.level.dimension.DimensionFile;

@Mixin(DimensionFile.class)
public interface AccessorDimensionFile {
	@Accessor("parentFolder")
	File getParentFolder();
}
