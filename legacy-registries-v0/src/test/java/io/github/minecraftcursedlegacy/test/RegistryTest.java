package io.github.minecraftcursedlegacy.test;

import io.github.minecraftcursedlegacy.api.registry.Id;
import io.github.minecraftcursedlegacy.api.registry.Registries;
import io.github.minecraftcursedlegacy.api.registry.TileEntities;
import io.github.minecraftcursedlegacy.api.registry.TileItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.recipe.SmeltingRecipeRegistry;
import net.minecraft.tile.Tile;
import net.minecraft.tile.entity.TileEntity;

public class RegistryTest implements ModInitializer {
	@Override
	public void onInitialize() {
		System.out.println("Hello, Fabric World!");
		item = Registries.ITEM_TYPE.register(new Id("modid:item"),
				i -> new BasicItem(i).setTexturePosition(5, 0).setName("exampleItem"));
		tile = Registries.TILE.register(new Id("modid:tile"),
				i -> new BasicTile(i, false).setName("exampleBlock"));
		tileItem = TileItems.registerTileItem(new Id("modid:tile"), tile);


		tileWithEntity = Registries.TILE.register(new Id("modid:tile_with_entity"),
				i -> new BasicTileWithEntity(i).setName("exampleBlockWithEntity"));
		tileWithEntityItem = TileItems.registerTileItem(new Id("modid:tile_with_entity"), tileWithEntity);
		tileEntityClass = BasicTileWithEntity.BasicTileEntity.class;
		TileEntities.registerTileEntity(tileEntityClass, new Id("modid:tile_entity"));

		SmeltingRecipeRegistry.getInstance().addSmeltingRecipe(item.id, new ItemInstance(tile));
		SmeltingRecipeRegistry.getInstance().addSmeltingRecipe(Tile.DIRT.id, new ItemInstance(tileWithEntity));
	}

	public static ItemType item;
	public static Tile tile;
	public static ItemType tileItem;
	public static Tile tileWithEntity;
	public static ItemType tileWithEntityItem;
	public static Class<? extends TileEntity> tileEntityClass;
}
