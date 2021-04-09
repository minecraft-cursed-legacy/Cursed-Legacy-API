package io.github.minecraftcursedlegacy.registry.impl;

import io.github.minecraftcursedlegacy.api.registry.Id;
import net.minecraft.entity.Entity;

public class EntityType {
	private final Class<? extends Entity> clazz;
	private final String vanillaRegistryStringId;

	/**
	 * Protected constructor used only for vanilla entities.
	 * @param clazz the entity class.
	 * @param vanillaRegistryStringId the vanilla name of the entity.
	 */
	protected EntityType(Class<? extends Entity> clazz, String vanillaRegistryStringId) {
		this.clazz = clazz;
		this.vanillaRegistryStringId = vanillaRegistryStringId;
	}

	public EntityType(Class<? extends Entity> clazz, Id id) {
		this.clazz = clazz;
		this.vanillaRegistryStringId = id.toString();
	}

	public Class<? extends Entity> getClazz() {
		return clazz;
	}

	public String getVanillaRegistryStringId() {
		return vanillaRegistryStringId;
	}

	public Id getId() {
		return new Id(vanillaRegistryStringId);
	}
}
