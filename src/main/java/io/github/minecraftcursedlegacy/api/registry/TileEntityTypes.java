package io.github.minecraftcursedlegacy.api.registry;

import io.github.minecraftcursedlegacy.impl.registry.TileEntityType;
import net.minecraft.entity.TileEntity;

public class TileEntityTypes {
	/**
	 * Use this to create TileEntityTypes for your own tile entities.
	 * @param clazz the tile entity class.
	 * @param id the identifier used in the TileEntityType registry.
	 */
	public static TileEntityType createTileEntityType(Class<? extends TileEntity> clazz, Id id) {
		return new TileEntityType(clazz, id);
	}
}
