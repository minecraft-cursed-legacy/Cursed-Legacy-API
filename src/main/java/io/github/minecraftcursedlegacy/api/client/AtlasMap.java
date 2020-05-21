package io.github.minecraftcursedlegacy.api.client;

import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;

import io.github.minecraftcursedlegacy.impl.client.AtlasMapper;

/**
 * Utility methods for having {@link ItemType}s which use textures from other than the main item atlas
 *
 * @author Chocohead
 */
public final class AtlasMap {
	private AtlasMap() {
	}

	/**
	 * Register the given atlas for use with the {@link ItemType} corresponding to the given 
	 * {@link ItemInstance#itemId} when it has the given {@link ItemInstance#getDamage()}
	 *
	 * @param stack The item type and damage which uses the given atlas
	 * @param atlas The location of the custom atlas
	 *
	 * @throws NullPointerException If atlas is null
	 */
	public static void registerAtlas(ItemInstance stack, String atlas) {
		registerAtlas(stack.itemId, stack.getDamage(), atlas);
	}

	/**
	 * Register the given atlas for use with the given {@link ItemType},
	 * regardless of what {@link ItemInstance#getDamage()} on an instance of it returns
	 *
	 * @param item The item which uses the given atlas
	 * @param atlas The location of the custom atlas
	 *
	 * @throws NullPointerException If atlas is null
	 */
	public static void registerAtlas(ItemType item, String atlas) {
		registerAtlas(item.id, atlas);
	}

	/**
	 * Register the given atlas for use with the {@link ItemType} corresponding to the given ID
	 * when {@link ItemInstance#getDamage()} on an instance of it is the same as the given meta
	 *
	 * @param itemID The ID of the item which uses the given atlas
	 * @param meta The damage value an instance must have to use this atlas
	 * @param atlas The location of the custom atlas
	 *
	 * @throws NullPointerException If atlas is null
	 */
	public static void registerAtlas(int itemID, int meta, String atlas) {
		AtlasMapper.registerAtlas(itemID, meta, atlas);
	}

	/**
	 * Register the given atlas for use with the {@link ItemType} corresponding to the given ID,
	 * regardless of what {@link ItemInstance#getDamage()} on an instance of it returns
	 *
	 * @param itemID The ID of the item which uses the given atlas
	 * @param atlas The location of the custom atlas
	 *
	 * @throws NullPointerException If atlas is null
	 */
	public static void registerAtlas(int itemID, String atlas) {
		AtlasMapper.registerDefaultAtlas(itemID, atlas);
	}

	/**
	 * Get the atlas the given {@link ItemType} corresponding to the given ID has
	 *
	 * @param itemID The ID of the item
	 *
	 * @return The location of the custom atlas or <code>null</code> if it uses the default one
	 */
	public static String getAtlas(int itemID) {
		return getAtlas(itemID, 0);
	}

	/**
	 * Get the atlas the given {@link ItemType} corresponding to the given ID has with the given damage 
	 *
	 * @param itemID The ID of the item
	 * @param meta The damage of an {@link ItemInstance}
	 *
	 * @return The location of the custom atlas or <code>null</code> if it uses the default one
	 */
	public static String getAtlas(int itemID, int meta) {
		return AtlasMapper.getAtlas(itemID, meta);
	}
}