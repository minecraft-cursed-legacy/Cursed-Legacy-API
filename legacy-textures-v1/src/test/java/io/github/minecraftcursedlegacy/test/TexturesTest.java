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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.tile.PlantTile;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;

public class TexturesTest implements ModInitializer {
	private static ItemType item, alsoItem;
	private static Tile cross, betterCross;
	private static Tile cube, redgrass;

	@SuppressWarnings("deprecation")
	@Override
	public void onInitialize() {
		// Legacy Atlas Mapping api
		item = Registries.ITEM_TYPE.register(new Id("modid:item_texture"),
				id -> new BasicItem(id).setTexturePosition(2, 0).setName("exampleTextureItem"));
		AtlasMap.registerAtlas(item, "/assets/modid/bc/item_textures.png");

		// set with the 1.1.0 model discovery api which uses choco's 0.x api generated atlas impl under the hood
		alsoItem = Registries.ITEM_TYPE.register(new Id("modid:item_texture_too"), id -> new BasicItem(id).setName("exampleTextureItemAlso"));

		// This one doesn't mess with the item texture and uses parented texture (you have to re-override a client side method to do this with PlantTile however)
		cross = Registries.TILE.register(new Id("modid:iron_grass"), id -> FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT ? new ClientNormalTileRenderingTallGrassTile(id).name("ironGrass") : new TallGrassTile(id).name("ironGrass"));
		TileItems.registerTileItem(new Id("modid:iron_grass"), cross);

		// This one does
		betterCross = Registries.TILE.register(new Id("modid:malachite_grass"), id -> new TallGrassTile(id).name("malachiteGrass"));
		TileItems.registerTileItem(new Id("modid:malachite_grass"), betterCross);

		cube = Registries.TILE.register(new Id("modid:cursed_legacy_block"), id -> new BasicTile(id, false).name("cursedLegacyBlock"));
		TileItems.registerTileItem(new Id("modid:cursed_legacy_block"), cube);

		redgrass = Registries.TILE.register(new Id("modid:red_grass"), id -> new CustomGrassBlockTile(id).name("redGrass"));
		TileItems.registerTileItem(new Id("modid:red_grass"), redgrass);

		Recipes.addShapelessRecipe(new ItemInstance(item), Tile.WOOD);
		Recipes.addShapelessRecipe(new ItemInstance(alsoItem), item);
		Recipes.addShapelessRecipe(new ItemInstance(cross), alsoItem);
		Recipes.addShapelessRecipe(new ItemInstance(betterCross), cross);
		Recipes.addShapelessRecipe(new ItemInstance(cube), ItemType.stick);
		Recipes.addShapelessRecipe(new ItemInstance(redgrass), cube);

		Translations.addItemTranslation(item, "Example Item");
		Translations.addItemTranslation(alsoItem, "Example Item Too");
		Translations.addTileTranslation(cross, "Example Cross Model Tile");
		Translations.addTileTranslation(betterCross, "Malachite Grass");
		Translations.addTileTranslation(cube, "Cursed Legacy Block");
		Translations.addTileTranslation(redgrass, "Red Grass");
	}

	static class TallGrassTile extends PlantTile {
		TallGrassTile(int id) {
			super(id, 69);
			this.hardness(0.0F);
			this.sounds(GRASS_SOUNDS);
		}
	}

	static class CustomGrassBlockTile extends Tile { // I would extend GrassTile but that tints the whole block based on grass colour which is cursed
		CustomGrassBlockTile(int id) {
			super(id, Material.ORGANIC);

			this.hardness(0.6F);
			this.sounds(GRASS_SOUNDS);
		}
	}
}