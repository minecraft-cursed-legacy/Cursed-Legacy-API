package io.github.minecraftcursedlegacy.test;

import io.github.minecraftcursedlegacy.api.registry.Id;
import io.github.minecraftcursedlegacy.api.registry.Registries;
import io.github.minecraftcursedlegacy.api.registry.TileItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.recipe.SmeltingRecipeRegistry;
import net.minecraft.tile.Tile;
import net.minecraft.tile.entity.TileEntity;

public class TileEntityTest implements ModInitializer {
	@Override
	public void onInitialize() {
		System.out.println("Hello, Fabric World!");
		tileWithEntity = Registries.TILE.register(new Id("modid:tile_with_entity"),
				i -> new BasicTileWithEntity(i).setName("exampleBlockWithEntity"));
		tileWithEntityItem = TileItems.registerTileItem(new Id("modid:tile_with_entity"), tileWithEntity);
		tileEntityClass = BasicTileWithEntity.BasicTileEntity.class;
		TileEntities.registerTileEntity(tileEntityClass, new Id("modid:tile_entity"));

		SmeltingRecipeRegistry.getInstance().addSmeltingRecipe(Tile.DIRT.id, new ItemInstance(tileWithEntity));
	}

	public static Tile tileWithEntity;
	public static ItemType tileWithEntityItem;
	public static Class<? extends TileEntity> tileEntityClass;

	public static class BasicTileWithEntity extends TileWithEntity {
		protected BasicTileWithEntity(int i) {
			super(i, 69, Material.DIRT);
		}

		@Override
		protected TileEntity createTileEntity() {
			return new BasicTileEntity();
		}

		static class BasicTileEntity extends TileEntity {
			@Override
			public void tick() {
				System.out.println("test tile tick!");
			}
		}
	}
}
