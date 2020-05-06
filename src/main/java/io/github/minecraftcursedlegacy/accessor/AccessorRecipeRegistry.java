package io.github.minecraftcursedlegacy.accessor;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.recipe.RecipeRegistry;
import net.minecraft.recipe.ShapedRecipe;

@Mixin(RecipeRegistry.class)
public interface AccessorRecipeRegistry {
	@Accessor("recipes")
	List<ShapedRecipe> getRecipes();
}
