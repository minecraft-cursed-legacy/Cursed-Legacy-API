package io.github.minecraftcursedlegacy.mixin.terrain;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import io.github.minecraftcursedlegacy.api.attacheddata.v1.DataManager;
import io.github.minecraftcursedlegacy.api.terrain.WorldType;
import io.github.minecraftcursedlegacy.impl.terrain.LevelGenImpl;
import io.github.minecraftcursedlegacy.impl.terrain.WorldTypeData;
import net.minecraft.level.Level;
import net.minecraft.level.LevelProperties;
import net.minecraft.level.dimension.Dimension;
import net.minecraft.level.gen.BiomeSource;
import net.minecraft.level.source.LevelSource;
import net.minecraft.level.source.OverworldLevelSource;

@Mixin(Dimension.class)
public class MixinDimension {
	@Inject(at = @At("HEAD"), method = "createBiomeSource", cancellable = true)
	protected void api_createBiomeSource(Canc) {
		Dimension self = (Dimension) (Object) this;					// convert to dimension
		LevelProperties properties = self.level.getProperties();	// retrieve the level properties
		WorldTypeData data = DataManager.LEVEL_PROPERTIES.getAttachedData(properties, LevelGenImpl.worldTypeData); // get world type data
		data.setCachedDimension(self);								// set the cached dimension of the data
		WorldType type = WorldType.getById(data.getTypeId());		// retrieve the world type 

		if (type != WorldType.DEFAULT) {							// only mess with non default in case another mod wants to mixin here for some reason
			self.biomeSource = type.createBiomeSource(self.level, data.getLoadedData()); // set the biome source
		}
	}

	public LevelSource api_createLevelSource() {
		Level level = ((Dimension) (Object) this).level;
		return new OverworldLevelSource(, this.level.getSeed());
	}
}
