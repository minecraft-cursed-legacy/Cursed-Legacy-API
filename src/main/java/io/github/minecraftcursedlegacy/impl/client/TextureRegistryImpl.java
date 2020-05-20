package io.github.minecraftcursedlegacy.impl.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class TextureRegistryImpl {
    private static boolean hacksEnabled = false;
    private static boolean canOpenGl = false;
    private static HashMap<Integer, Texture> texturemap = new HashMap<Integer, Texture>();
    private static HashMap<Integer, String> texturelimbo = new HashMap<Integer, String>();

    public static void addCustomTexture(int itemID, String texturePath) {
        if (canOpenGl) {
            doTexture(itemID, texturePath);
        } else {
            texturelimbo.put(itemID, texturePath);
        }
    }

    private static void doTexture(int itemID, String texturePath) {
        if (!hacksEnabled) {
            // Slick Utils dynamically allocates texture id's and minecraft hard codes them
            // so we loop 2000 times here to avoid mc textures
            // Yes this is as dumb as it seems
            for (int i = 0; i < 2000; ++i) {
                try {
                    TextureLoader.getTexture(texturePath.substring(texturePath.length() - 3).toUpperCase(Locale.US), ResourceLoader.getResourceAsStream(texturePath));
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1); // No Clue What To Do Here
                }
            }
            try {
                texturemap.put(itemID,
                        TextureLoader.getTexture(texturePath.substring(texturePath.length() - 3).toUpperCase(Locale.US), ResourceLoader.getResourceAsStream(texturePath)));
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1); // No Clue What To Do Here
            }
            Texture texture = texturemap.get(itemID);
            System.out.println("Texture loaded: "+texture);
            System.out.println(">> Image width: "+texture.getImageWidth());
            System.out.println(">> Image height: "+texture.getImageHeight());
            System.out.println(">> Texture width: "+texture.getTextureWidth());
            System.out.println(">> Texture height: "+texture.getTextureHeight());
            System.out.println(">> Texture ID: "+texture.getTextureID());
        }
    }

    public static Texture getTexture(int itemID) {
        if (!canOpenGl) {
            canOpenGl = true;
            for (int key : texturelimbo.keySet()) {
                String e = texturelimbo.get(key);
                doTexture(key, e);
            }
            return texturemap.get(itemID);
        } else {
            return texturemap.get(itemID);
        }
    }
}