package io.github.minecraftcursedlegacy.impl.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AtlasMapper {
	private static class ItemAtlasUsage {
		final Map<Integer, String> atlas = new HashMap<>();
		String defaultAtlas;
	}
	private static final Map<Integer, ItemAtlasUsage> ATLAS_MAP = new HashMap<>();
	private static final Logger LOGGER = LogManager.getLogger("Atlas Mapper");

	private static void ensureAtlasValid(String atlas) {
		if (atlas == null) throw new NullPointerException("Tried to register null atlas");
	}

	public static void registerAtlas(int itemID, int meta, String atlas) {
		ensureAtlasValid(atlas);
		Map<Integer, String> itemAtlas = ATLAS_MAP.computeIfAbsent(itemID, k -> new ItemAtlasUsage()).atlas;

		if (itemAtlas.containsKey(meta)) {
			if (!itemAtlas.get(meta).equals(atlas)) {
				LOGGER.warn("Duplicate atlas definition for item:meta {}:{}", itemID, meta);
			}

			return;
		}

		itemAtlas.put(meta, atlas);
	}

	public static void registerDefaultAtlas(int itemID, String atlas) {
		ensureAtlasValid(atlas);
		ItemAtlasUsage itemAtlas = ATLAS_MAP.computeIfAbsent(itemID, k -> new ItemAtlasUsage());

		if (itemAtlas.defaultAtlas != null) {
			if (!itemAtlas.defaultAtlas.equals(atlas)) {
				LOGGER.warn("Duplicate default atlas definition for item {}", itemID);
			}

			return;
		}

		itemAtlas.defaultAtlas = atlas;
	}

	public static String getAtlas(int itemID, int meta) {
		ItemAtlasUsage itemAtlas = ATLAS_MAP.get(itemID);
		return itemAtlas != null ? itemAtlas.atlas.getOrDefault(meta, itemAtlas.defaultAtlas) : null;
	}
}