package io.github.minecraftcursedlegacy.api.recipe;

import io.github.minecraftcursedlegacy.recipes.accessor.AccessorRecipeRegistry;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.source.OverworldLevelSource;
import net.minecraft.recipe.DyeRecipes;
import net.minecraft.recipe.RecipeRegistry;

/**
 * Class that acts as an interface for the recipe registry.
 */
public final class Recipes {
	private Recipes() {
		// NO-OP
	}

	/**
	 * Adds a shaped recipe to the game.
	 * For Example, the vanilla cake recipe:<br/>&nbsp;<b>{@code Recipes.addShapedRecipe(new ItemInstance(ItemType.cake, 1), "AAA", "BEB", "CCC", 'A', ItemType.milk, 'B', ItemType.sugar, 'C', ItemType.wheat, 'E', ItemType.egg);}</b>
	 * @param result the item instance the recipe crafts.
	 * @param recipe the recipe, shaped.
	 * @see the code in {@link RecipeRegistry} for more examples on how to use this.
	 */
	public static void addShapedRecipe(ItemInstance result, Object... recipe) {
		((AccessorRecipeRegistry) RecipeRegistry.getInstance()).invokeAddShapedRecipe(result, recipe);
	}

	/**
	 * Adds a shapeless recipe to the game.
	 * For Example, the vanilla bonemeal recipe:<br/>&nbsp<b>{@code arg.addShapelessRecipe(new ItemInstance(ItemType.dyePowder, 3, 15), ItemType.bone);}</b>
	 * @param result the item instance the recipe crafts.
	 * @param ingredients a list of various item instances, item types, and tiles which are the required ingredients of this recipe.
	 * @see the code in {@link DyeRecipes} for more examples on how to use this.
	 */
	public static void addShapelessRecipe(ItemInstance result, Object... ingredients) {
		((AccessorRecipeRegistry) RecipeRegistry.getInstance()).invokeAddShapelessRecipe(result, ingredients);
	}
}
