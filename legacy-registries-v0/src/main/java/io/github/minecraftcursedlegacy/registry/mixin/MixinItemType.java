package io.github.minecraftcursedlegacy.registry.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import io.github.minecraftcursedlegacy.registry.impl.IdSetter;
import net.minecraft.item.ItemType;

@Mixin(ItemType.class)
public class MixinItemType implements IdSetter {
	@Shadow
	@Final
	@Mutable
	private int id;

	@Override
	public void setId(int id) {
		this.id = id;
	}
}
