package io.github.minecraftcursedlegacy.api.client;

import io.github.minecraftcursedlegacy.impl.client.TextureRegistryImpl;

public class TextureRegistry {
	private TextureRegistry() {
	}

	/**
	 * Sets a custom texture for an item id
	 * Eg:
	 * io.github.minecraftcursedlegacy.api.client.TextureRegistry.addCustomTexture(280, "assets/modid/tbgn3p08.png"); //280 is the stick id
	 * @param itemID Id of the item you want to change
	 * @param texturePath Eg: "assets/modid/tbgn3p08.png"
	 */
	public static void addCustomTexture(int itemID, String texturePath){
		TextureRegistryImpl.addCustomTexture(itemID, texturePath);
	}
}