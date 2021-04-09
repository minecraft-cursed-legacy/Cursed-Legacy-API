package io.github.minecraftcursedlegacy.api.registry;

import io.github.minecraftcursedlegacy.registry.impl.EntityType;
import net.minecraft.entity.Entity;

public class EntityTypes {
	/**
	 * Use this to create EntityTypes for your own entities.
	 * @param clazz the entity class.
	 * @param id the identifier used in the EntityType registry.
	 */
	public static EntityType createEntityType(Class<? extends Entity> clazz, Id id) {
		return new EntityType(clazz, id);
	}
}
