package io.github.minecraftcursedlegacy.accessor.recipe;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.item.ItemInstance;
import net.minecraft.recipe.RecipeRegistry;

@Mixin(RecipeRegistry.class)
public interface AccessorRecipeRegistry {
	@Invoker("addShapedRecipe")
	void invokeAddShapedRecipe(ItemInstance arg, Object... objects);

	@Invoker("addShapelessRecipe")
	void invokeAddShapelessRecipe(ItemInstance arg, Object... objects);
}
