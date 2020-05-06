package io.github.minecraftcursedlegacy.impl.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.IntFunction;

import io.github.minecraftcursedlegacy.api.registry.Id;
import io.github.minecraftcursedlegacy.api.registry.Registry;
import io.github.minecraftcursedlegacy.impl.Hacks;
import io.github.minecraftcursedlegacy.mixin.AccessorTileItem;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.ItemType;
import net.minecraft.item.TileItem;
import net.minecraft.tile.Tile;

public class RegistryImpl implements ModInitializer {
	private static int currentItemtypeId = 256;
	private static int currentTileId = 0;
	private static int currentTileItemId = 0;

	private static final Map<Tile, TileItem> T_2_TI = new HashMap<>();

	private static int nextItemTypeId() {
		while (ItemType.byId[currentItemtypeId] != null) {
			++currentItemtypeId;
		}

		return currentItemtypeId;
	}

	private static int nextTileItemId() {
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

	private static class ItemTypeRegistry extends Registry<ItemType> {
		private ItemTypeRegistry(Id registryName) {
			super(ItemType.class, registryName, null);

			// add vanilla item types
			for (int i = 0; i < ItemType.byId.length; ++i) {
				ItemType value = ItemType.byId[i];

				if (value instanceof TileItem) {
					T_2_TI.put(Tile.BY_ID[((AccessorTileItem) value).getTileId()], (TileItem) value);
				}

				if (value != null) {
					String idPart = value.getTranslationKey();

					if (idPart == null) {
						idPart = "itemtype";
					} else {
						idPart = idPart.substring(5);
					}

					this.byRegistryId.put(new Id(idPart + "_" + i), value);
					this.bySerialisedId.put(i, value);
				}
			}
		}

		@Override
		public <E extends ItemType> E registerValue(Id id, E value) {
			throw new UnsupportedOperationException("Use register(Id, IntFunction<ItemType>) instead, since item types need to use the provided int serialised ids in their constructor!");
		}

		/**
		 * Item Types are weird.
		 */
		@Override
		public <E extends ItemType> E register(Id id, IntFunction<E> valueProvider) {
			return super.register(id, rawSID -> valueProvider.apply(rawSID - 256));
		}

		@Override
		protected int getNextSerialisedId() {
			return nextItemTypeId();
		}

		@Override
		protected void beforeRemap() {
			int size = ItemType.byId.length;
			System.arraycopy(new ItemType[size], 0, ItemType.byId, 0, size);
		}

		@Override
		protected void onRemap(ItemType remappedValue, int newSerialisedId) {
			ItemType.byId[newSerialisedId] = remappedValue;
			((IdSetter) remappedValue).setId(newSerialisedId);
		}

		@Override
		protected int getStartSerialisedId() {
			return 1;
		}

		@Override
		protected void postRemap() {
			//TODO remap recipes here
		}

		private TileItem addTileItem(Id id, Tile tile, BiFunction<Integer, Tile, TileItem> constructor) {
			TileItem item = constructor.apply(nextTileItemId() - 256, tile);
			this.byRegistryId.put(id, item);
			this.bySerialisedId.put(item.id, item);
			return item;
		}
	}

	static class TileRegistry extends Registry<Tile> {
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

	public static Registry<ItemType> ITEM_TYPE;
	public static Registry<Tile> TILE;

	static {
		Tile.BED.hashCode(); // make sure tiles are initialised
		ITEM_TYPE = new ItemTypeRegistry(new Id("api:item_type"));
		TILE = new TileRegistry(new Id("api:tile"));
	}
}
