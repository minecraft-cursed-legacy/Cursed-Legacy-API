package io.github.minecraftcursedlegacy.accessor;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeRegistry;

@Mixin(RecipeRegistry.class)
public interface AccessorRecipeRegistry {
	@Accessor("recipes")
	List<Recipe> getRecipes();
}
