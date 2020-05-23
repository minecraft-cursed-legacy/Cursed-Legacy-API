package io.github.minecraftcursedlegacy.api.registry;

import java.util.function.IntFunction;

import net.minecraft.item.ItemType;
import net.minecraft.item.PlaceableTileItem;
import net.minecraft.item.TileItem;
import net.minecraft.tile.Tile;

import io.github.minecraftcursedlegacy.impl.registry.RegistryImpl;

/**
 * Utilities for adding and registering tile items.
 */
public class TileItems {
	/**
	 * Adds a registered tile item for the tile.
	 * @param id the id of the tile item.
	 * @param value the tile this tile item is for.
	 * @return the tile item created.
	 * @deprecated Prefer using {@link #registerTileItem(Id, Tile)} (which creates a 
	 * 				{@link PlaceableTileItem} for the given tile rather than a {@link TileItem}) so 
	 * 				that it will respect the item meta when deciding on the tile to place in the world.
	 */
	@Deprecated
	public static TileItem addRegisteredTileItem(Id id, Tile value) {
		return RegistryImpl.addTileItem(id, value, itemID -> new TileItem(itemID, value));
	}

	/**
	 * Register an {@link ItemType} for the given {@link Tile}.
	 *
	 * @param id The identifier of the item to be registered
	 * @param tile The tile the item is for
	 *
	 * @return An item for the given tile
	 */
	public static ItemType registerTileItem(Id id, Tile tile) {
		return registerTileItem(id, tile, PlaceableTileItem::new);
	}

	/**
	 * Register an {@link ItemType} for the given {@link Tile} created using the given factory.
	 *
	 * @param id The identifier of the item to be registered
	 * @param tile The tile the item is for
	 * @param itemFactory A factory for creating the item, given the item ID to use
	 *
	 * @return An item for the given tile
	 */
	public static <I extends PlaceableTileItem> I registerTileItem(Id id, Tile tile, IntFunction<I> itemFactory) {
		return RegistryImpl.addTileItem(id, tile, itemFactory);
	}
}
