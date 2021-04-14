package io.github.minecraftcursedlegacy.api.registry;

import io.github.minecraftcursedlegacy.accessor.registry.AccessorTileEntity;
import io.github.minecraftcursedlegacy.impl.registry.RegistryImpl;
import net.minecraft.item.ItemType;
import net.minecraft.item.PlaceableTileItem;
import net.minecraft.item.TileItem;
import net.minecraft.tile.Tile;
import net.minecraft.tile.entity.TileEntity;

import java.util.function.IntFunction;

/**
 * Utilities for adding and registering tile entities.
 */
public final class TileEntities {
	/**
	 * Adds a registered tile entity.
	 * @param clazz the class of the tile entity to register.
	 * @param id the ID to register the tile entity as.
	 */
	public static void registerTileEntity(Class<? extends TileEntity> clazz, Id id) {
		AccessorTileEntity.register(clazz, id.toString());
	}
}
