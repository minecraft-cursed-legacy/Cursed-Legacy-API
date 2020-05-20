package io.github.minecraftcursedlegacy.api.client;

import io.github.minecraftcursedlegacy.impl.client.TextureRegistryImpl;

public class TextureRegistry {
    public static void addCustomTexture(int itemID, String texturePath){
        TextureRegistryImpl.addCustomTexture(itemID, texturePath);
    }
}