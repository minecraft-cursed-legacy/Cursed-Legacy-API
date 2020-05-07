package io.github.minecraftcursedlegacy.api.registry;

import io.github.minecraftcursedlegacy.impl.registry.RegistryImpl;
import net.minecraft.item.TileItem;
import net.minecraft.tile.Tile;

/**
 * Utilities for adding and registering tile items.
 */
public class TileItems {
	/**
	 * Adds a registered tile item for the tile.
	 * @param id the id of the tile item.
	 * @param value the tile this tile item is for.
	 * @return the tile item created.
	 */
	public static TileItem addRegisteredTileItem(Id id, Tile value) {
		return RegistryImpl.addTileItem(id, value, TileItem::new);
	}
}
