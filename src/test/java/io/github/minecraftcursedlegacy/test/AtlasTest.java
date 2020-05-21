package io.github.minecraftcursedlegacy.test;

import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.tile.Tile;

import net.fabricmc.api.ModInitializer;

import io.github.minecraftcursedlegacy.api.client.AtlasMap;
import io.github.minecraftcursedlegacy.api.recipe.Recipes;
import io.github.minecraftcursedlegacy.api.registry.Id;
import io.github.minecraftcursedlegacy.api.registry.Registries;

public class AtlasTest implements ModInitializer {
	private static ItemType item, alsoItem;

	@Override
	public void onInitialize() {
		item = Registries.ITEM_TYPE.register(new Id("modid:item_texture"),
				id -> new BasicItem(id).setTexturePosition(2, 0).setName("exampleTextureItem"));
		AtlasMap.registerAtlas(item, "/assets/modid/bc/item_textures.png");

		alsoItem = Registries.ITEM_TYPE.register(new Id("modid:item_texture_too"), id -> {
			ItemType item = new BasicItem(id).setName("exampleTextureItemAlso");
			return item.method_458(AtlasMap.registerSprite(item, "/assets/modid/bc/iron_gear.png"));
		});

		Recipes.addShapelessRecipe(new ItemInstance(item), Tile.WOOD);
		Recipes.addShapelessRecipe(new ItemInstance(alsoItem), item);
	}
}