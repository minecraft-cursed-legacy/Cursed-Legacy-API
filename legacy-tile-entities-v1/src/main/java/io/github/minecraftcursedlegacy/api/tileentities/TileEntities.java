package io.github.minecraftcursedlegacy.api.tileentities;

import io.github.minecraftcursedlegacy.accessor.tileentities.AccessorTileEntity;
import io.github.minecraftcursedlegacy.api.registry.Id;
import net.minecraft.tile.entity.TileEntity;

import java.util.Locale;

/**
 * Utilities for registering and retrieving tile entity classes.
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

	/**
	 * Retrieves the tile entity class registered with the given ID.
	 * @param id the ID of the tile entity class.
	 * @return the tile entity class with that ID.
	 */
	public static Class<? extends TileEntity> get(Id id) {
		return AccessorTileEntity.getIdToClassMap().get(id.toString());
	}

	/**
	 * Retrieves the id for a given tile entity class.
	 * @param clazz the tile entity class.
	 * @return the ID of that tile entity class.
	 */
	public static Id getId(Class<? extends TileEntity> clazz) {
		return new Id(getStringId(clazz).toLowerCase(Locale.ROOT));
	}

	/**
	 * Retrieves the string id for a given tile entity class.
	 * @param clazz the tile entity class.
	 * @return the string ID of that tile entity class.
	 */
	public static String getStringId(Class<? extends TileEntity> clazz) {
		return AccessorTileEntity.getClassToIdMap().get(clazz);
	}
}
