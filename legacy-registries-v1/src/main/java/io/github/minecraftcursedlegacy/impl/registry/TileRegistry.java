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

import io.github.minecraftcursedlegacy.api.registry.Id;
import io.github.minecraftcursedlegacy.api.registry.Registry;
import net.minecraft.item.ItemType;
import net.minecraft.tile.Tile;
import net.minecraft.util.io.CompoundTag;

class TileRegistry extends Registry<Tile> {
	private final Map<Tile, Boolean> ticksRandomly = new HashMap<>();
	private final Map<Tile, Boolean> isFullOpaque = new HashMap<>();
	private final Map<Tile, Boolean> hasTileEntity = new HashMap<>();
	private final Map<Tile, Integer> field_1941 = new HashMap<>();
	private final Map<Tile, Boolean> isAir = new HashMap<>();
	private final Map<Tile, Integer> luminances = new HashMap<>();
	private final Map<Tile, Boolean> multipleStates = new HashMap<>();

	TileRegistry(Id registryName) {
		super(Tile.class, registryName, null);

		VanillaIds.initialiseTiles();

		// add vanilla tiles
		for (int i = 0; i < Tile.BY_ID.length; ++i) {
			Tile value = Tile.BY_ID[i];

			if (value != null) {
				this.byRegistryId.put(VanillaIds.getVanillaId(value), value);
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
		return RegistryImpl.nextTileId();
	}

	@Override
	protected int getStartSerialisedId() {
		return 1; // Because 0 is taken by air and is a null entry because notch spaghetti.
	}

	@Override
	public Tile getById(Id id) {
		return super.getById(VanillaIds.correctLegacyTileId(id));
	}
	
	// refactor note: not keeping the old code in the ()V beforeRemap method 'cuz literally no one will be manually invoking that 

	@Override
	protected void beforeRemap(CompoundTag tag) {
		int size = Tile.BY_ID.length;

		// Clear the tile array

		for (int i = 1; i < size; i++) { // Starting at 1 as microoptimisation because 0 is taken by a forced null value: Air
			Tile tile = Tile.BY_ID[i];

			if (tile != null) {
				ticksRandomly.put(tile, Tile.TICKS_RANDOMLY[i]);
				isFullOpaque.put(tile, Tile.FULL_OPAQUE[i]);
				hasTileEntity.put(tile, Tile.HAS_TILE_ENTITY[i]);
				field_1941.put(tile, Tile.field_1941[i]);
				isAir.put(tile, Tile.IS_AIR[i]);
				luminances.put(tile, Tile.LUMINANCES[i]);
				multipleStates.put(tile, Tile.MULTIPLE_STATES[i]);
			}
		}

		System.arraycopy(new Tile[size], 0, Tile.BY_ID, 0, size);
		System.arraycopy(new boolean[size], 0, Tile.TICKS_RANDOMLY, 0, size);
		System.arraycopy(new boolean[size], 0, Tile.FULL_OPAQUE, 0, size);
		System.arraycopy(new boolean[size], 0, Tile.HAS_TILE_ENTITY, 0, size);
		System.arraycopy(new int[size], 0, Tile.field_1941, 0, size);
		System.arraycopy(new boolean[size], 0, Tile.IS_AIR, 0, size);
		System.arraycopy(new int[size], 0, Tile.LUMINANCES, 0, size);
		System.arraycopy(new boolean[size], 0, Tile.MULTIPLE_STATES, 0, size);

		VanillaIds.fixOldIds(tag, true);
	}

	@Override
	protected void onRemap(Tile remappedValue, int newSerialisedId) {
		// Set the new values in the arrays
		Tile.BY_ID[newSerialisedId] = remappedValue;
		((IdSetter) remappedValue).setId(newSerialisedId);

		// tile item
		ItemType tileItem = RegistryImpl.T_2_TI.get(remappedValue);

		if (tileItem != null) {
			((HasParentId) tileItem).setParentId(newSerialisedId);
		}

		Tile.TICKS_RANDOMLY[newSerialisedId] = ticksRandomly.getOrDefault(remappedValue, false);
		Tile.FULL_OPAQUE[newSerialisedId] = isFullOpaque.getOrDefault(remappedValue, false);
		Tile.HAS_TILE_ENTITY[newSerialisedId] = hasTileEntity.getOrDefault(remappedValue, false);
		Tile.field_1941[newSerialisedId] = field_1941.getOrDefault(remappedValue, 0);
		Tile.IS_AIR[newSerialisedId] = isAir.getOrDefault(remappedValue, false);
		Tile.LUMINANCES[newSerialisedId] = luminances.getOrDefault(remappedValue, 0);
		Tile.MULTIPLE_STATES[newSerialisedId] = multipleStates.getOrDefault(remappedValue, false);
	}
}