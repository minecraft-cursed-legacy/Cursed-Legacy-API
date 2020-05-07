package io.github.minecraftcursedlegacy.accessor;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.item.ItemInstance;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeRegistry;

@Mixin(RecipeRegistry.class)
public interface AccessorRecipeRegistry {
	@Accessor("recipes")
	List<Recipe> getRecipes();

	@Invoker("addShapedRecipe")
	void invokeAddShapedRecipe(ItemInstance arg, Object... objects);

	@Invoker("addShapelessRecipe")
	void invokeAddShapelessRecipe(ItemInstance arg, Object... objects);
}
