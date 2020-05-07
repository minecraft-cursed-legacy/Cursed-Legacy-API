package io.github.minecraftcursedlegacy.impl.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import io.github.minecraftcursedlegacy.api.registry.Id;
import io.github.minecraftcursedlegacy.api.registry.Registry;
import io.github.minecraftcursedlegacy.impl.Hacks;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.ItemType;
import net.minecraft.item.TileItem;
import net.minecraft.tile.Tile;

public class RegistryImpl implements ModInitializer {
	private static int currentItemtypeId = 256;
	private static int currentTileId = 0;
	private static int currentTileItemId = 0;

	static final Map<Tile, TileItem> T_2_TI = new HashMap<>();

	static int nextItemTypeId() {
		while (ItemType.byId[currentItemtypeId] != null) {
			++currentItemtypeId;
		}

		return currentItemtypeId;
	}

	static int nextTileItemId() {
		while (ItemType.byId[currentTileItemId] != null) {
			++currentTileItemId;
		}

		return currentTileItemId - 256;
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
			return 1;
		}

		@Override
		protected void beforeRemap() {
			int size = Tile.BY_ID.length;
			System.arraycopy(new Tile[size], 0, Tile.BY_ID, 0, size);
		}

		@Override
		protected void onRemap(Tile remappedValue, int newSerialisedId) {
			Tile.BY_ID[newSerialisedId] = remappedValue;
			((IdSetter) remappedValue).setId(newSerialisedId);

			// tile item
			((ParentIdSetter) T_2_TI.get(remappedValue)).setParentId(newSerialisedId);
		}
	}

	public static TileItem addTileItem(Id id, Tile value, BiFunction<Integer, Tile, TileItem> constructor) {
		return ((ItemTypeRegistry) ITEM_TYPE).addTileItem(id, value, constructor);
	}

	@Override
	public void onInitialize() {
		Hacks.hack = Registry::lockAll;
	}

	public static final Registry<ItemType> ITEM_TYPE;
	public static final Registry<Tile> TILE;

	static {
		Tile.BED.hashCode(); // make sure tiles are initialised
		ITEM_TYPE = new ItemTypeRegistry(new Id("api:item_type"));
		TILE = new TileRegistry(new Id("api:tile"));
	}
}
