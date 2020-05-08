package io.github.minecraftcursedlegacy.mixin;

import java.io.File;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.minecraftcursedlegacy.accessor.AccessorDimensionFile;
import io.github.minecraftcursedlegacy.impl.registry.RegistryRemapper;
import net.minecraft.level.dimension.DimensionData;
import net.minecraft.level.dimension.DimensionFile;
import net.minecraft.level.storage.McRegionLevelStorage;
import net.minecraft.level.storage.OldLevelStorage;

@Mixin(McRegionLevelStorage.class)
public class MixinMcRegionLevelStorage extends OldLevelStorage {
	public MixinMcRegionLevelStorage(File file) {
		super(file);
	}

	@Inject(at = @At("RETURN"), method = "createDimensionFile")
	private void addRemapping(String string, boolean flag, CallbackInfoReturnable<DimensionData> info) {
		DimensionFile data = (DimensionFile) info.getReturnValue();
		RegistryRemapper.remap(new File(((AccessorDimensionFile) data).getParentFolder(), "pomf_registry.dat"));
	}
}
