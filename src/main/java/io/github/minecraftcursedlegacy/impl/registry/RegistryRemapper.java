package io.github.minecraftcursedlegacy.impl.registry;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import net.minecraft.util.io.AbstractTag;
import net.minecraft.util.io.CompoundTag;

public class RegistryRemapper {
	public static void remap(File file) {
		// just in case
		file.getParentFile().mkdirs();

		try {
			if (!file.createNewFile()) {
				CompoundTag data = null;

				try (DataInputStream dis = new DataInputStream(new GZIPInputStream(new FileInputStream(file)))) {
					data = (CompoundTag) AbstractTag.readTag(dis);
				}

				CompoundTag data0 = data;

				RegistryImpl.registries().forEach(registry -> {
					String key = registry.getRegistryName().toString();

					if (data0.containsKey(key)) {
						registry.remap(data0.getCompoundTag(key));
					}
				});

				try (DataOutputStream dos = new DataOutputStream(new GZIPOutputStream(new FileOutputStream(file)))) {
					
				}
			} else {
				CompoundTag data = new CompoundTag();

				RegistryImpl.registries().forEach(registry -> {
					data.put(registry.getRegistryName().toString(), registry.toTag());
				});

				try (DataOutputStream dos = new DataOutputStream(new GZIPOutputStream(new FileOutputStream(file)))) {
					AbstractTag.writeTag(data, dos);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException("[Cursed Legacy API] Error while running registry remapper", e);
		}
	}

	static final Logger LOGGER = Logger.getLogger("Cursed Legacy API");
}
