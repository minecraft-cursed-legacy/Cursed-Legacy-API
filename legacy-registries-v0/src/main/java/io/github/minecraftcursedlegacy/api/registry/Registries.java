package io.github.minecraftcursedlegacy.api.registry;

import io.github.minecraftcursedlegacy.registry.impl.EntityType;
import io.github.minecraftcursedlegacy.registry.impl.RegistryImpl;
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
}
