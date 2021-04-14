package io.github.minecraftcursedlegacy.impl.registry.client;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.texture.TextureManager;

class CustomAtlas implements Atlas {
	private static final short MAX_SLOTS = 16 * 16;
	private final BufferedImage image = FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT ? new BufferedImage(16 * 16, 16 * 16, BufferedImage.TYPE_INT_ARGB) : null;
	private short remainingSlots = MAX_SLOTS;
	private boolean needsRefresh;
	private int textureID = -1;

	@Override
	public String getName() {
		return "<Generated Atlas>";
	}

	public boolean hasRoom() {
		return remainingSlots > 0;
	}

	public int allocate(String sprite) {
		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
			try (InputStream in = CustomAtlas.class.getResourceAsStream(sprite)) {
				return allocate(ImageIO.read(in));
			} catch (IOException e) {
				throw new RuntimeException("Error reading in sprite from " + sprite, e);
			}
		} else {
			//Skip doing the work on a server
			return MAX_SLOTS - remainingSlots--;
		}
	}

	public int allocate(BufferedImage sprite) {
		assert FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;

		int width = sprite.getWidth();
		int height = sprite.getHeight();
		if (width != 16) throw new IllegalArgumentException("Expected a 16x16 sprite but was " + width + 'x' + height);

		int[] rgb = new int[width * height];
		sprite.getRGB(0, 0, width, height, rgb, 0, width);

		int slot = MAX_SLOTS - remainingSlots--;
		int x = slot % 16;
		int y = slot / 16;

		image.setRGB(x, y, 16, 16, rgb, 0, width);
		needsRefresh = true;

		return slot;
	}

	@Override
	@Environment(EnvType.CLIENT)
	public int getTextureID(TextureManager manager) {
		if (textureID < 0) {
			textureID = manager.glLoadImage(image);
			needsRefresh = false;
		} else if (needsRefresh) {
			needsRefresh = false;
			manager.method_1089(image, textureID);
		}

		return textureID;
	}
}