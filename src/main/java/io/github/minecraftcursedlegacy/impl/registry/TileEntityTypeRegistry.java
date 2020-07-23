package io.github.minecraftcursedlegacy.impl.registry;

import io.github.minecraftcursedlegacy.accessor.AccessorTileEntity;
import io.github.minecraftcursedlegacy.api.registry.Id;
import io.github.minecraftcursedlegacy.api.registry.Registry;

import java.util.HashMap;

public class TileEntityTypeRegistry extends Registry<TileEntityType> {
	/**
	 * Creates a new registry object.
	 *
	 * @param registryName the identifier for this registry.
	 */
	public TileEntityTypeRegistry(Id registryName) {
		super(TileEntityType.class, registryName, null);

		// add vanilla tile entities
		AccessorTileEntity.getIdToClassMap().forEach((id, clazz) -> {
			if (clazz != null) {
				TileEntityType type = new TileEntityType(clazz, id == null ? "tileentity" : id);
				if (id == null) {
					id = "tileentity";
				} else {
					id = id.toLowerCase();
				}

				this.byRegistryId.put(new Id(id), type);
				this.bySerialisedId.put(getNextSerialisedId(), type);
			}
		});
	}

	@Override
	protected void beforeRemap() {
		AccessorTileEntity.setClassToIdMap(new HashMap<>());
		AccessorTileEntity.setIdToClassMap(new HashMap<>());
	}

	@Override
	protected void onRemap(TileEntityType remappedValue, int newSerialisedId) {
		AccessorTileEntity.callRegister(remappedValue.getClazz(), remappedValue.getVanillaRegistryStringId());
	}
}
