package io.github.minecraftcursedlegacy.accessor.registry;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.item.ItemInstance;
import net.minecraft.recipe.ShapelessRecipe;

@Mixin(ShapelessRecipe.class)
public interface AccessorShapelessRecipe {
	@Accessor("output")
	ItemInstance getOutput();
	@Accessor("input")
	List<ItemInstance> getInput();
}
