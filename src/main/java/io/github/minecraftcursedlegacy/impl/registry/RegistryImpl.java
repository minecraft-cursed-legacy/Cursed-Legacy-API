package io.github.minecraftcursedlegacy.impl.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import io.github.minecraftcursedlegacy.api.registry.Id;
import io.github.minecraftcursedlegacy.api.registry.Registry;
import io.github.minecraftcursedlegacy.impl.Hacks;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.ItemType;
import net.minecraft.item.TileItem;
import net.minecraft.tile.Tile;

public class RegistryImpl implements ModInitializer {
	private static int currentItemtypeId = 0;
	private static int currentTileId = 0;
	private static final Map<Id, Registry<?>> REGISTRIES = new HashMap<>();

	public static Stream<Registry<?>> registries() {
		return REGISTRIES.values().stream();
	}

	private static int nextItemTypeId() {
		while (ItemType.byId[currentItemtypeId + 256] != null) {
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

	public static void addRegistry(Registry<?> registry) {
		REGISTRIES.put(registry.getRegistryName(), registry);
	}

	public static Registry<Tile> createTileRegistry(Id registryName) {
		return new TileRegistry(registryName);
	}

	public static Registry<ItemType> createItemTypeRegistry(Id registryName) {
		return new ItemTypeRegistry(registryName);
	}

	private static class ItemTypeRegistry extends Registry<ItemType> {
		private ItemTypeRegistry(Id registryName) {
			super(ItemType.class, registryName, null);

			// add vanilla item types
			for (int i = 0; i < ItemType.byId.length; ++i) {
				ItemType value = ItemType.byId[i];

				if (value != null) {
					this.byRegistryId.put(new Id(value.getTranslationKey().substring(5)), value);
					this.bySerialisedId.put(i, value);
				}
			}
		}

		@Override
		public <E extends ItemType> E register(Id id, E value) {
			throw new UnsupportedOperationException("Use register(Id, IntFunction<E>) instead, since item types need to use the provided int serialised ids in their constructor!");
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

		private void addTileItem(Id id, Tile tile) {
			TileItem item = new TileItem(tile.id - 256, tile);
			this.byRegistryId.put(id, item);
			this.bySerialisedId.put(item.id, item);
		}
	}

	private static class TileRegistry extends Registry<Tile> {
		private TileRegistry(Id registryName) {
			super(Tile.class, registryName, null);

			// add vanilla tiles
			for (int i = 0; i < Tile.BY_ID.length; ++i) {
				Tile value = Tile.BY_ID[i];

				if (value != null) {
					this.byRegistryId.put(new Id(value.getName()), value);
					this.bySerialisedId.put(i, value);
				}
			}
		}

		@Override
		public <E extends Tile> E register(Id id, E value) {
			throw new UnsupportedOperationException("Use register(Id, IntFunction<E>) instead, since tiles need to use the provided int serialised ids in their constructor!");
		}

		@Override
		protected int getNextSerialisedId() {
			return nextTileId();
		}

		@Override
		protected void onRegister(int serialisedId, Id id, Tile value) {
			((ItemTypeRegistry) Registry.ITEM_TYPE).addTileItem(id, value);
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
		}
	}

	@Override
	public void onInitialize() {
		Hacks.hack = Registry::lockAll;
	}
}
