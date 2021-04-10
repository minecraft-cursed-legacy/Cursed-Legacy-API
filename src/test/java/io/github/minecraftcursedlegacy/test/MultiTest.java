package io.github.minecraftcursedlegacy.test;

import io.github.minecraftcursedlegacy.api.ModPostInitializer;
import io.github.minecraftcursedlegacy.api.client.AtlasMap;
import io.github.minecraftcursedlegacy.api.recipe.Recipes;
import io.github.minecraftcursedlegacy.api.registry.Id;
import io.github.minecraftcursedlegacy.api.registry.Registries;
import io.github.minecraftcursedlegacy.api.registry.Translations;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.tile.Tile;

public class MultiTest implements ModInitializer, ModPostInitializer {
	private static ItemType item, alsoItem;

	@SuppressWarnings("deprecation")
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

		Translations.addItemTranslation(item, "Example Item");
		Translations.addItemTranslation(alsoItem, "Example Item Too");
	}

	@Override
	public void onPostInitialize() {
		assistRegistryTest(); // Because this ensures initialisation and registration is complete.
	}

	private static void assistRegistryTest() {
		Recipes.addShapelessRecipe(new ItemInstance(RegistryTest.item, 2), Tile.DIRT, Tile.SAND);
		Recipes.addShapedRecipe(new ItemInstance(RegistryTest.tile), "##", '#', Tile.DIRT);
		
		Translations.addTileTranslation(RegistryTest.tile, "Example Block");
		Translations.addItemTranslation(RegistryTest.item, "Example Item");
	}
}