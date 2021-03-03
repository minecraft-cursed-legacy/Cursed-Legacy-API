package io.github.minecraftcursedlegacy.impl.registry;

import io.github.minecraftcursedlegacy.api.registry.Id;
import net.minecraft.entity.TileEntity;

public class TileEntityType {
	private final Class<? extends TileEntity> clazz;
	private final String vanillaRegistryStringId;

	/**
	 * Protected constructor used only for vanilla tile entities.
	 * @param clazz the entity class.
	 * @param vanillaRegistryStringId the vanilla name of the entity.
	 */
	protected TileEntityType(Class<? extends TileEntity> clazz, String vanillaRegistryStringId) {
		this.clazz = clazz;
		this.vanillaRegistryStringId = vanillaRegistryStringId;
	}

	/**
	 * Prefer use of {@link io.github.minecraftcursedlegacy.api.registry.TileEntityTypes#create}
	 */
	public TileEntityType(Class<? extends TileEntity> clazz, Id id) {
		this.clazz = clazz;
		this.vanillaRegistryStringId = id.toString();
	}

	public Class<? extends TileEntity> getClazz() {
		return clazz;
	}

	public String getVanillaRegistryStringId() {
		return vanillaRegistryStringId;
	}

	public Id getId() {
		return new Id(vanillaRegistryStringId);
	}
}
