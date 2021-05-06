/*
 * Copyright (c) 2020 The Cursed Legacy Team.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package io.github.minecraftcursedlegacy.test;

import io.github.minecraftcursedlegacy.api.client.AtlasMap;
import io.github.minecraftcursedlegacy.api.recipe.Recipes;
import io.github.minecraftcursedlegacy.api.registry.Id;
import io.github.minecraftcursedlegacy.api.registry.Registries;
import io.github.minecraftcursedlegacy.api.registry.TileItems;
import io.github.minecraftcursedlegacy.api.registry.Translations;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.tile.PlantTile;
import net.minecraft.tile.Tile;

public class TexturesTest implements ModInitializer {
	private static ItemType item, alsoItem;
	private static Tile cross, betterCross;
	private static Tile cube;

	@SuppressWarnings("deprecation")
	@Override
	public void onInitialize() {
		// Legacy Atlas Map
		item = Registries.ITEM_TYPE.register(new Id("modid:item_texture"),
				id -> new BasicItem(id).setTexturePosition(2, 0).setName("exampleTextureItem"));
		AtlasMap.registerAtlas(item, "/assets/modid/bc/item_textures.png");

		// set with the 1.1.0 model discovery api which uses choco's 0.x api generated atlas impl under the hood
		alsoItem = Registries.ITEM_TYPE.register(new Id("modid:item_texture_too"), id -> new BasicItem(id).setName("exampleTextureItemAlso"));

		cross = Registries.TILE.register(new Id("modid:iron_grass"), id -> new BasicTile(id, false).name("ironGrass"));
		betterCross = Registries.TILE.register(new Id("modid:malachite_grass"), id -> new MalachiteGrassTile(id).name("malachiteGrass"));
		cube = Registries.TILE.register(new Id("modid:cursed_legacy_block"), id -> new BasicTile(id, false).name("cursedLegacyBlock"));
		TileItems.registerTileItem(new Id("modid:iron_grass"), cross);
		TileItems.registerTileItem(new Id("modid:malachite_grass"), betterCross);
		TileItems.registerTileItem(new Id("modid:cursed_legacy_block"), cube);

		Recipes.addShapelessRecipe(new ItemInstance(item), Tile.WOOD);
		Recipes.addShapelessRecipe(new ItemInstance(alsoItem), item);
		Recipes.addShapelessRecipe(new ItemInstance(cross), alsoItem);
		Recipes.addShapelessRecipe(new ItemInstance(betterCross), cross);
		Recipes.addShapelessRecipe(new ItemInstance(cube), ItemType.stick);

		Translations.addItemTranslation(item, "Example Item");
		Translations.addItemTranslation(alsoItem, "Example Item Too");
		Translations.addTileTranslation(cross, "Example Cross Model Tile");
		Translations.addTileTranslation(betterCross, "Malachite Grass");
		Translations.addTileTranslation(cube, "Cursed Legacy Block");
	}
	
	static class MalachiteGrassTile extends PlantTile {
		MalachiteGrassTile(int id) {
			super(id, 69);
			this.hardness(0.0F);
			this.sounds(Tile.GRASS_SOUNDS);
		}
	}
}