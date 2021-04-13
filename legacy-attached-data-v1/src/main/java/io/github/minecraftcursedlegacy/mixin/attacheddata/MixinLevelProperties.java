package io.github.minecraftcursedlegacy.mixin.attacheddata;

import java.util.HashMap;
import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.minecraftcursedlegacy.api.attacheddata.v1.AttachedData;
import io.github.minecraftcursedlegacy.api.attacheddata.v1.DataManager;
import io.github.minecraftcursedlegacy.api.attacheddata.v1.SimpleDataStorage;
import io.github.minecraftcursedlegacy.api.registry.Id;
import net.minecraft.level.LevelProperties;
import net.minecraft.util.io.CompoundTag;

/**
 * Mixin to add attached data to level properties.
 * @since 1.0.0
 */
@Mixin(LevelProperties.class)
public class MixinLevelProperties implements SimpleDataStorage {
	private Map<Id, AttachedData> api_attachedDataMap = new HashMap<>();

	@Inject(at = @At("RETURN"), method = "<init>(Lnet/minecraft/util/io/CompoundTag;)V")
	private void api_onLoadProperties(CompoundTag tag, CallbackInfo info) {
		if (tag.containsKey("moddedData")) {
			CompoundTag data = tag.getCompoundTag("moddedData");

			// Load Data
			DataManager.LEVEL_PROPERTIES.loadData((LevelProperties) (Object) this, data);
		}
	}

	@Inject(at = @At("RETURN"), method = "<init>(Lnet/minecraft/level/LevelProperties;)V")
	private void api_onCopyProperties(LevelProperties from, CallbackInfo info) {
		DataManager.LEVEL_PROPERTIES.copyData(from, (LevelProperties) (Object) this);
	}

	@Inject(at = @At("RETURN"), method = "updateProperties")
	private void updateProperties(CompoundTag writeTo, CompoundTag playerData, CallbackInfo info) {
		writeTo.put("moddedData", this.getModdedTag());
	}

	@Override
	public Map<Id, AttachedData> getRawAttachedDataMap() {
		return this.api_attachedDataMap;
	}
}
