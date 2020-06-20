package io.github.minecraftcursedlegacy.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import io.github.minecraftcursedlegacy.impl.registry.SmeltingRecipeSetter;
import net.minecraft.recipe.SmeltingRecipeRegistry;

@Mixin(SmeltingRecipeRegistry.class)
public abstract class MixinSmeltingRecipeRegistry implements SmeltingRecipeSetter {
	@Shadow
	private Map recipes;

	@Override
	public void setRecipes(Map recipes) {
		this.recipes = recipes;
	}
}
