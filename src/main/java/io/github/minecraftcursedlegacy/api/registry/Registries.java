package io.github.minecraftcursedlegacy.api.registry;

import io.github.minecraftcursedlegacy.impl.registry.EntityType;
import io.github.minecraftcursedlegacy.impl.registry.RegistryImpl;
import io.github.minecraftcursedlegacy.impl.registry.TileEntityType;
import net.minecraft.item.ItemType;
import net.minecraft.tile.Tile;

/**
 * Class containing the {@link Registry registries} that are part of the api.
 */
public final class Registries {
	/**
	 * Registry for Item Types.
	 */
	public static final Registry<ItemType> ITEM_TYPE = RegistryImpl.ITEM_TYPE;
	/**
	 * Registry for Tiles.
	 */
	public static final Registry<Tile> TILE = RegistryImpl.TILE;

	/**
	 * Registry for Entity types.
	 */
	public static final Registry<EntityType> ENTITY_TYPE = RegistryImpl.ENTITY_TYPE;

	/**
	 * Registry for Tile Entity types.
	 */
	public static final Registry<TileEntityType> TILE_ENTITY_TYPE = RegistryImpl.TILE_ENTITY_TYPE;
}
