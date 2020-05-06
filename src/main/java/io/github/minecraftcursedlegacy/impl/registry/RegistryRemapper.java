package io.github.minecraftcursedlegacy.impl.registry;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import io.github.minecraftcursedlegacy.api.registry.Registry;
import net.minecraft.util.io.AbstractTag;
import net.minecraft.util.io.CompoundTag;

public class RegistryRemapper {
	public static void remap(File file) {
		// just in case
		file.getParentFile().mkdirs();

		try {
			if (!file.createNewFile()) {
				CompoundTag data = null;

				// read
				LOGGER.info("Reading Registry Data.");
				try (DataInputStream dis = new DataInputStream(new GZIPInputStream(new FileInputStream(file)))) {
					data = (CompoundTag) AbstractTag.readTag(dis);
				}

				CompoundTag newData = new CompoundTag();
				Iterator<Registry<?>> iter = RegistryImpl.registries().iterator();

				// remap and add new data
				LOGGER.info("Remapping Registries.");
				while (iter.hasNext()) {
					Registry<?> registry = iter.next();
					String key = registry.getRegistryName().toString();

					if (data.containsKey(key)) {
						newData.put(key, registry.remap(data.getCompoundTag(key)));
					} else {
						newData.put(key, registry.toTag());
					}
				}

				LOGGER.info("Writing Registry Data.");
				// write
				try (DataOutputStream dos = new DataOutputStream(new GZIPOutputStream(new FileOutputStream(file)))) {
					AbstractTag.writeTag(newData, dos);
				}
			} else {
				CompoundTag data = new CompoundTag();

				// add data
				LOGGER.info("Collecting Registry Data.");
				RegistryImpl.registries().forEach(registry -> {
					data.put(registry.getRegistryName().toString(), registry.toTag());
				});

				// write
				LOGGER.info("Writing Registry Data.");
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
