package io.github.minecraftcursedlegacy.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.item.PlaceableTileItem;

@Mixin(PlaceableTileItem.class)
public interface AccessorPlaceableTileItem {
	@Accessor("field_2216")
	int getTileId();
}
