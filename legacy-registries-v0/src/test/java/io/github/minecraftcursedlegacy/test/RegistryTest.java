package io.github.minecraftcursedlegacy.test;

import io.github.minecraftcursedlegacy.api.registry.Id;
import io.github.minecraftcursedlegacy.api.registry.Registries;
import io.github.minecraftcursedlegacy.api.registry.TileItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.recipe.SmeltingRecipeRegistry;
import net.minecraft.tile.Tile;

public class RegistryTest implements ModInitializer {
	@Override
	public void onInitialize() {
		System.out.println("Hello, Fabric World!");
		item = Registries.ITEM_TYPE.register(new Id("modid:item"),
				i -> new BasicItem(i).setTexturePosition(5, 0).setName("exampleItem"));
		tile = Registries.TILE.register(new Id("modid:tile"),
				i -> new BasicTile(i, false).setName("exampleBlock"));
		tileItem = TileItems.registerTileItem(new Id("modid:tile"), tile);

		SmeltingRecipeRegistry.getInstance().addSmeltingRecipe(item.id, new ItemInstance(tile));
	}

	public static ItemType item;
	public static Tile tile;
	public static ItemType tileItem;
}
