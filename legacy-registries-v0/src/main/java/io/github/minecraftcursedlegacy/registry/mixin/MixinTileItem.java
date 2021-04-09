package io.github.minecraftcursedlegacy.registry.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import io.github.minecraftcursedlegacy.registry.impl.ParentIdSetter;
import net.minecraft.item.TileItem;

@Mixin(TileItem.class)
public class MixinTileItem implements ParentIdSetter {
	@Shadow
	private int tileId;

	@Override
	public void setParentId(int id) {
		this.tileId = id;
	}
}
