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

package io.github.minecraftcursedlegacy.impl.texture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.primitives.Ints;

import io.github.minecraftcursedlegacy.api.registry.Registries;
import io.github.minecraftcursedlegacy.impl.registry.sync.RegistryRemapper;
import io.github.minecraftcursedlegacy.impl.texture.Atlas.FileAtlas;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.texture.TextureManager;

public class AtlasMapper implements ClientModInitializer {
	private static class ItemAtlasUsage {
		final Map<Integer, Atlas> atlas = new HashMap<>();
		Atlas defaultAtlas;
	}

	private static final Map<Integer, ItemAtlasUsage> ATLAS_MAP = new HashMap<>();
	private static final List<CustomAtlas> CUSTOM_ATLASI = new ArrayList<>();
	private static final Logger LOGGER = LogManager.getLogger("Atlas Mapper");

	private static void ensureAtlasValid(String atlas) {
		if (atlas == null) throw new NullPointerException("Tried to register null atlas");
	}

	public static void registerAtlas(int itemID, int meta, String atlas) {
		ensureAtlasValid(atlas);
		Map<Integer, Atlas> itemAtlas = ATLAS_MAP.computeIfAbsent(itemID, k -> new ItemAtlasUsage()).atlas;

		if (itemAtlas.containsKey(meta)) {
			if (!itemAtlas.get(meta).getName().equals(atlas)) {
				LOGGER.warn("Duplicate atlas definition for item:meta {}:{}", itemID, meta);
			}

			return;
		}

		itemAtlas.put(meta, FileAtlas.forAtlas(atlas));
	}

	public static void registerDefaultAtlas(int itemID, String atlas) {
		ensureAtlasValid(atlas);
		ItemAtlasUsage itemAtlas = ATLAS_MAP.computeIfAbsent(itemID, k -> new ItemAtlasUsage());

		if (itemAtlas.defaultAtlas != null) {
			if (!itemAtlas.defaultAtlas.getName().equals(atlas)) {
				LOGGER.warn("Duplicate default atlas definition for item {}", itemID);
			}

			return;
		}

		itemAtlas.defaultAtlas = FileAtlas.forAtlas(atlas);
	}

	private static void ensureSpriteValid(String atlas) {
		if (atlas == null) throw new NullPointerException("Tried to register null sprite");
	}

	private static CustomAtlas nextAtlas() {
		for (CustomAtlas atlas : CUSTOM_ATLASI) {
			if (atlas.hasRoom()) {
				return atlas;
			}
		}

		CustomAtlas atlas = new CustomAtlas();
		CUSTOM_ATLASI.add(atlas);
		return atlas;
	}

	public static int registerSprite(int itemID, int meta, String sprite) {
		ensureSpriteValid(sprite);
		Map<Integer, Atlas> itemAtlas = ATLAS_MAP.computeIfAbsent(itemID, k -> new ItemAtlasUsage()).atlas;

		if (itemAtlas.containsKey(meta)) {
			throw new IllegalArgumentException("Duplicate atlas definition for item:meta " + itemID + ':' + meta);
		}

		CustomAtlas atlas = nextAtlas();
		itemAtlas.put(meta, atlas);
		return atlas.allocate(sprite);
	}

	public static int registerDefaultSprite(int itemID, String sprite) {
		ensureSpriteValid(sprite);
		ItemAtlasUsage itemAtlas = ATLAS_MAP.computeIfAbsent(itemID, k -> new ItemAtlasUsage());

		if (itemAtlas.defaultAtlas != null) {
			throw new IllegalArgumentException("Duplicate default atlas definition for item " + itemID);
		}

		CustomAtlas atlas = nextAtlas();
		itemAtlas.defaultAtlas = atlas;
		return atlas.allocate(sprite);
	}

	@Environment(EnvType.CLIENT)
	public static OptionalInt getAtlas(TextureManager manager, int itemID, int meta) {
		ItemAtlasUsage itemAtlas = ATLAS_MAP.get(itemID);
		if (itemAtlas == null) return OptionalInt.empty();

		Atlas atlas = itemAtlas.atlas.getOrDefault(meta, itemAtlas.defaultAtlas);
		return atlas != null ? OptionalInt.of(atlas.getTextureID(manager)) : OptionalInt.empty();
	}

	@Override
	public void onInitializeClient() {
		Registries.ITEM_TYPE.getRemapEvent().register((registry, diff) -> {
			RegistryRemapper.LOGGER.info("Remapping custom texture atlases.");
			Map<Integer, ItemAtlasUsage> displaced = new HashMap<>();

			for (int id : Ints.toArray(ATLAS_MAP.keySet())) {
				int remappedID = diff.getNewSerialisedId(id);

				if (id != remappedID) {
					displaced.put(remappedID, ATLAS_MAP.remove(id));
				}
			}

			ATLAS_MAP.putAll(displaced);
		});
	}
}