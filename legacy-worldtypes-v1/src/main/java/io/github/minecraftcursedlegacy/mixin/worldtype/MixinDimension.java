package io.github.minecraftcursedlegacy.mixin.worldtype;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.minecraftcursedlegacy.api.attacheddata.v1.DataManager;
import io.github.minecraftcursedlegacy.api.worldtype.WorldType;
import io.github.minecraftcursedlegacy.impl.worldtype.WorldTypeData;
import io.github.minecraftcursedlegacy.impl.worldtype.WorldTypeImpl;
import net.minecraft.level.LevelProperties;
import net.minecraft.level.dimension.Dimension;
import net.minecraft.level.source.LevelSource;

@Mixin(Dimension.class)
public class MixinDimension {
	@Inject(at = @At("HEAD"), method = "createBiomeSource", cancellable = true)
	private void api_createBiomeSource(CallbackInfo info) {
		Dimension self = (Dimension) (Object) this;					// convert to dimension
		LevelProperties properties = self.level.getProperties();	// retrieve the level properties
		WorldTypeData data = DataManager.LEVEL_PROPERTIES.getAttachedData(properties, WorldTypeImpl.worldTypeData); // get world type data
		WorldType type = WorldType.getById(data.getTypeId());		// retrieve the world type 

		if (type != WorldType.DEFAULT) {							// only mess with non default in case another mod wants to mixin here for some reason
			self.biomeSource = type.createBiomeSource(self.level, data.getOrCreateLoadedData(type.storesAdditionalData())); // set the biome source
			info.cancel(); // prevent default
		}
	}

	@Inject(at = @At("HEAD"), method = "createLevelSource", cancellable = true)
	private void api_createLevelSource(CallbackInfoReturnable<LevelSource> info) {
		Dimension self = (Dimension) (Object) this;					// convert to dimension
		LevelProperties properties = self.level.getProperties();	// retrieve the level properties
		WorldTypeData data = DataManager.LEVEL_PROPERTIES.getAttachedData(properties, WorldTypeImpl.worldTypeData); // get world type data
		WorldType type = WorldType.getById(data.getTypeId());		// retrieve the world type 

		if (type != WorldType.DEFAULT) {							// only mess with non default in case another mod wants to mixin here for some reason
			info.setReturnValue(type.createChunkGenerator(self.level, data.getOrCreateLoadedData(type.storesAdditionalData()))); // set the custom chunk generator
		}
	}
}
