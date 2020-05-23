package io.github.minecraftcursedlegacy.impl.client;

import java.util.HashMap;
import java.util.Map;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.client.texture.TextureManager;

interface Atlas {
	String getName();

	@Environment(EnvType.CLIENT)
	int getTextureID(TextureManager manager);

	class FileAtlas implements Atlas {
		private static final Map<String, FileAtlas> ATLASI = new HashMap<>();
		private final String location;

		public static FileAtlas forAtlas(String atlas) {
			return ATLASI.computeIfAbsent(atlas, FileAtlas::new);
		}

		private FileAtlas(String location) {
			this.location = location;
		}

		@Override
		public String getName() {
			return location;
		}

		@Override
		@Environment(EnvType.CLIENT)
		public int getTextureID(TextureManager manager) {
			return manager.getTextureId(location);
		}

		@Override
		public int hashCode() {
			return location.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			return this == obj || (obj instanceof FileAtlas && location.equals(((FileAtlas) obj).location));
		}
	}
}