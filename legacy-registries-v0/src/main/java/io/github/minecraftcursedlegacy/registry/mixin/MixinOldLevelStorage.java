package io.github.minecraftcursedlegacy.registry.mixin;

import java.io.File;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.minecraftcursedlegacy.registry.accessor.AccessorDimensionFile;
import io.github.minecraftcursedlegacy.registry.impl.RegistryRemapper;
import net.minecraft.level.dimension.DimensionData;
import net.minecraft.level.dimension.DimensionFile;
import net.minecraft.level.storage.OldLevelStorage;

@Mixin(OldLevelStorage.class)
public class MixinOldLevelStorage {
	@Shadow
	@Final
	private File path;

	@Inject(at = @At("RETURN"), method = "createDimensionFile")
	private void addRemapping(String string, boolean flag, CallbackInfoReturnable<DimensionData> info) {
		DimensionFile data = (DimensionFile) info.getReturnValue();
		RegistryRemapper.remap(new File(((AccessorDimensionFile) data).getParentFolder(), "pomf_registry.dat"));
	}
}
