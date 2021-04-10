package io.github.minecraftcursedlegacy.registry.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.function.IntFunction;

import net.minecraft.item.ItemType;
import net.minecraft.tile.Tile;

import net.fabricmc.api.ModInitializer;
import io.github.minecraftcursedlegacy.api.registry.Id;
import io.github.minecraftcursedlegacy.api.registry.Registry;
import io.github.minecraftcursedlegacy.impl.Hacks;
import io.github.minecraftcursedlegacy.registry.accessor.AccessorEntityRegistry;

public class RegistryImpl implements ModInitializer {
	private static int currentItemtypeId = Tile.BY_ID.length;
	private static int currentTileId = 1;

	static final Map<Tile, ItemType> T_2_TI = new HashMap<>();

	static int nextItemTypeId() {
		while (ItemType.byId[currentItemtypeId] != null) {
			++currentItemtypeId;
		}

		return currentItemtypeId;
	}

	private static int nextTileId() {
		while (Tile.BY_ID[currentTileId] != null) {
			++currentTileId;
		}

		return currentTileId;
	}

	private static class TileRegistry extends Registry<Tile> {
		private TileRegistry(Id registryName) {
			super(Tile.class, registryName, null);

			// add vanilla tiles
			for (int i = 0; i < Tile.BY_ID.length; ++i) {
				Tile value = Tile.BY_ID[i];

				if (value != null) {
					String idPart = value.method_1597();

					if (idPart == null) {
						idPart = "tile";
					} else {
						idPart = idPart.substring(5);
					}

					this.byRegistryId.put(new Id(idPart + "_" + i), value);
					this.bySerialisedId.put(i, value);
				}
			}
		}

		@Override
		public <E extends Tile> E registerValue(Id id, E value) {
			throw new UnsupportedOperationException("Use register(Id, IntFunction<E>) instead, since tiles need to use the provided int serialised ids in their constructor!");
		}

		@Override
		protected int getNextSerialisedId() {
			return nextTileId();
		}

		@Override
		protected int getStartSerialisedId() {
			return 1; // Because 0 is taken by air and is a null entry because notch spaghetti.
		}

		@Override
		protected void beforeRemap() {
			int size = Tile.BY_ID.length;

			// Clear the tile array
			System.arraycopy(new Tile[size], 0, Tile.BY_ID, 0, size);
		}

		@Override
		protected void onRemap(Tile remappedValue, int newSerialisedId) {
			// Set the new values in the arrays
			Tile.BY_ID[newSerialisedId] = remappedValue;
			((IdSetter) remappedValue).setId(newSerialisedId);

			// tile item
			ItemType tileItem = T_2_TI.get(remappedValue);

			if (tileItem != null) {
				((ParentIdSetter) tileItem).setParentId(newSerialisedId);
			}
		}
	}

	public static <I extends ItemType> I addTileItem(Id id, Tile value, IntFunction<I> constructor) {
		return ((ItemTypeRegistry) ITEM_TYPE).addTileItem(id, value, constructor);
	}

	@Override
	public void onInitialize() {
		Hacks.hack = Registry::lockAll;
	}

	public static final Registry<ItemType> ITEM_TYPE;
	public static final Registry<Tile> TILE;
	public static final Registry<EntityType> ENTITY_TYPE;

	static {
		//noinspection ResultOfMethodCallIgnored
		Tile.BED.hashCode(); // make sure tiles are initialised
		AccessorEntityRegistry.getIdToClassMap(); // make sure entities are initialised
		ITEM_TYPE = new ItemTypeRegistry(new Id("api:item_type"));
		TILE = new TileRegistry(new Id("api:tile"));
		ENTITY_TYPE = new EntityTypeRegistry(new Id("api:entity_type"));
	}
}
