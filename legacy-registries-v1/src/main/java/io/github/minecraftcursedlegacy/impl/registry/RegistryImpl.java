/*
 * Copyright (c) 2020 The Cursed Legacy Team.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package io.github.minecraftcursedlegacy.impl.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.IntFunction;

import io.github.minecraftcursedlegacy.accessor.registry.AccessorEntityRegistry;
import io.github.minecraftcursedlegacy.api.networking.PluginChannel;
import io.github.minecraftcursedlegacy.api.networking.PluginChannelRegistry;
import io.github.minecraftcursedlegacy.api.registry.Id;
import io.github.minecraftcursedlegacy.api.registry.Registry;
import io.github.minecraftcursedlegacy.api.registry.RegistryEntryAddedCallback;
import io.github.minecraftcursedlegacy.impl.Hacks;
import io.github.minecraftcursedlegacy.impl.registry.sync.RegistrySyncChannelS2C;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.item.ItemType;
import net.minecraft.tile.Tile;
import net.minecraft.util.io.CompoundTag;

public class RegistryImpl implements ModInitializer {
	@Override
	public void onInitialize() {
		Hacks.hack = Registry::lockAll;

		PluginChannelRegistry.registerPluginChannel(syncChannel = new RegistrySyncChannelS2C());
	}
	

	public static <I extends ItemType> I addTileItem(Id id, Tile value, IntFunction<I> constructor) {
		return ((ItemTypeRegistry) ITEM_TYPE).addTileItem(id, value, constructor);
	}

	public static <T> Event<RegistryEntryAddedCallback<T>> createEvent(Class<T> clazz) {
		return EventFactory.createArrayBacked(RegistryEntryAddedCallback.class, listeners -> (object, id, rawId) -> {
			for (RegistryEntryAddedCallback<T> listener : listeners) {
				listener.onEntryAdded(object, id, rawId);
			}
		});
	}

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

	public static final Registry<ItemType> ITEM_TYPE;
	public static final Registry<Tile> TILE;
	public static final Registry<EntityType> ENTITY_TYPE;

	private static int currentItemtypeId = Tile.BY_ID.length;
	private static int currentTileId = 1;

	static final Map<Tile, ItemType> T_2_TI = new HashMap<>();

	// Sync Stuff
	public static PluginChannel syncChannel;
	public static CompoundTag registryData; // this is used server side only

	static {
		//noinspection ResultOfMethodCallIgnored
		Tile.BED.hashCode(); // make sure tiles are initialised
		AccessorEntityRegistry.getIdToClassMap(); // make sure entities are initialised
		ITEM_TYPE = new ItemTypeRegistry(new Id("api:item_type"));
		TILE = new TileRegistry(new Id("api:tile"));
		ENTITY_TYPE = new EntityTypeRegistry(new Id("api:entity_type"));
	}

	private static class TileRegistry extends Registry<Tile> {
		private final Map<Tile, Boolean> ticksRandomly = new HashMap<>();
		private final Map<Tile, Boolean> isFullOpaque = new HashMap<>();
		private final Map<Tile, Boolean> hasTileEntity = new HashMap<>();
		private final Map<Tile, Integer> field_1941 = new HashMap<>();
		private final Map<Tile, Boolean> field_1942 = new HashMap<>();
		private final Map<Tile, Integer> field_1943 = new HashMap<>();
		private final Map<Tile, Boolean> field_1944 = new HashMap<>();

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

			for (int i = 1; i < size; i++) { // Starting at 1 as microoptimisation because 0 is taken by a forced null value: Air
				Tile tile = Tile.BY_ID[i];

				if (tile != null) {
					ticksRandomly.put(tile, Tile.TICKS_RANDOMLY[i]);
					isFullOpaque.put(tile, Tile.FULL_OPAQUE[i]);
					hasTileEntity.put(tile, Tile.HAS_TILE_ENTITY[i]);
					field_1941.put(tile, Tile.field_1941[i]);
					field_1942.put(tile, Tile.field_1942[i]);
					field_1943.put(tile, Tile.field_1943[i]);
					field_1944.put(tile, Tile.field_1944[i]);
				}
			}

			System.arraycopy(new Tile[size], 0, Tile.BY_ID, 0, size);
			System.arraycopy(new boolean[size], 0, Tile.TICKS_RANDOMLY, 0, size);
			System.arraycopy(new boolean[size], 0, Tile.FULL_OPAQUE, 0, size);
			System.arraycopy(new boolean[size], 0, Tile.HAS_TILE_ENTITY, 0, size);
			System.arraycopy(new int[size], 0, Tile.field_1941, 0, size);
			System.arraycopy(new boolean[size], 0, Tile.field_1942, 0, size);
			System.arraycopy(new int[size], 0, Tile.field_1943, 0, size);
			System.arraycopy(new boolean[size], 0, Tile.field_1944, 0, size);
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

			Tile.TICKS_RANDOMLY[newSerialisedId] = ticksRandomly.getOrDefault(remappedValue, false);
			Tile.FULL_OPAQUE[newSerialisedId] = isFullOpaque.getOrDefault(remappedValue, false);
			Tile.HAS_TILE_ENTITY[newSerialisedId] = hasTileEntity.getOrDefault(remappedValue, false);
			Tile.field_1941[newSerialisedId] = field_1941.getOrDefault(remappedValue, 0);
			Tile.field_1942[newSerialisedId] = field_1942.getOrDefault(remappedValue, false);
			Tile.field_1943[newSerialisedId] = field_1943.getOrDefault(remappedValue, 0);
			Tile.field_1944[newSerialisedId] = field_1944.getOrDefault(remappedValue, false);
		}
	}
}
