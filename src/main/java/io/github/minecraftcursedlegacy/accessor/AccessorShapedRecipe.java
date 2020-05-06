package io.github.minecraftcursedlegacy.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.item.ItemInstance;
import net.minecraft.recipe.ShapedRecipe;

@Mixin(ShapedRecipe.class)
public interface AccessorShapedRecipe {
	@Accessor("ingredients")
	ItemInstance[] getIngredients();
	@Accessor("output")
	ItemInstance getOutput();
}
