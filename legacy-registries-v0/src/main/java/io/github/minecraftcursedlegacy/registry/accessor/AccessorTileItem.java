package io.github.minecraftcursedlegacy.registry.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.item.TileItem;

@Mixin(TileItem.class)
public interface AccessorTileItem {
	@Accessor("tileId")
	int getTileId();
}
