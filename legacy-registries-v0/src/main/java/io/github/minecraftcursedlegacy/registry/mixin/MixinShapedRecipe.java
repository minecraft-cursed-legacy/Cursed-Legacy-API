package io.github.minecraftcursedlegacy.registry.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import io.github.minecraftcursedlegacy.registry.impl.IdSetter;
import net.minecraft.recipe.ShapedRecipe;

@Mixin(ShapedRecipe.class)
public class MixinShapedRecipe implements IdSetter {
	@Shadow
	@Final
	@Mutable
	private int outputId;

	@Override
	public void setId(int id) {
		this.outputId = id;
	}
}
