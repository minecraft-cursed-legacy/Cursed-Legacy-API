package io.github.minecraftcursedlegacy.registry.impl;

import io.github.minecraftcursedlegacy.api.registry.Id;
import io.github.minecraftcursedlegacy.api.registry.Registry;
import io.github.minecraftcursedlegacy.registry.accessor.AccessorEntityRegistry;
import net.minecraft.entity.Entity;

import java.util.HashMap;
import java.util.Map;

public class EntityTypeRegistry extends Registry<EntityType> {
	private int currentId = 0;

	/**
	 * Creates a new registry object.
	 *
	 * @param registryName the identifier for this registry.
	 */
	public EntityTypeRegistry(Id registryName) {
		super(EntityType.class, registryName, null);

		// add vanilla entities
		AccessorEntityRegistry.getIdToClassMap().forEach((intId, clazz) -> {
			if (clazz != null) {
				String idPart = AccessorEntityRegistry.getClassToStringIdMap().get(clazz);

				EntityType type = new EntityType(clazz, idPart == null ? "entity" : idPart);
				if (idPart == null) {
					idPart = "entity";
				} else {
					idPart = idPart.toLowerCase();
				}

				this.byRegistryId.put(new Id(idPart), type);
				this.bySerialisedId.put(intId, type);
			}
		});
	}

	@Override
	protected int getNextSerialisedId() {
		Map<Integer, Class<? extends Entity>> idToClass = AccessorEntityRegistry.getIdToClassMap();
		while (idToClass.containsKey(currentId)) {
			++currentId;
		}

		return currentId;
	}

	@Override
	protected int getStartSerialisedId() {
		return 1; //Maybe this could be changed to 0, not sure if vanilla would like an entity having 0 as an id.
	}

	@Override
	protected void beforeRemap() {
		AccessorEntityRegistry.setIdToClassMap(new HashMap<>());
		AccessorEntityRegistry.setClassToIdMap(new HashMap<>());
		AccessorEntityRegistry.setStringIdToClassMap(new HashMap<>());
		AccessorEntityRegistry.setClassToStringIdMap(new HashMap<>());
	}

	@Override
	protected void onRemap(EntityType remappedValue, int newSerialisedId) {
		AccessorEntityRegistry.callRegister(remappedValue.getClazz(), remappedValue.getVanillaRegistryStringId(), newSerialisedId);
	}
}
