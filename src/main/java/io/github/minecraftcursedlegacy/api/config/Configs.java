package io.github.minecraftcursedlegacy.api.config;

import java.io.File;
import java.io.IOException;

import javax.annotation.Nullable;

import io.github.minecraftcursedlegacy.api.registry.Id;
import net.fabricmc.loader.api.FabricLoader;
import tk.valoeghese.zoesteriaconfig.api.ZoesteriaConfig;
import tk.valoeghese.zoesteriaconfig.api.container.WritableConfig;
import tk.valoeghese.zoesteriaconfig.api.template.ConfigTemplate;

/**
 * Class for loading and saving mod configs.
 */
public final class Configs {
	private Configs() {
		// NO-OP
	}

	/**
	 * Retrieves or creates a new {@link WritableConfig}, based on the given id.
	 * @param configId the identifier of the config.
	 * @param defaults defaults for the config, which can be created with a builder.
	 * @return the {@link WritableConfig} instance with the config data in this file.
	 * @throws IOException if there is an error creating the config file.
	 */
	public static WritableConfig loadOrCreate(Id configId, @Nullable ConfigTemplate defaults) throws IOException {
		File directory = new File(FabricLoader.getInstance().getConfigDirectory(), configId.getNamespace());
		directory.mkdirs();
		File configFile = new File(directory, configId.getName() + ".cfg");
		boolean createNew = configFile.createNewFile();

		WritableConfig result = ZoesteriaConfig.loadConfigWithDefaults(configFile, defaults);

		if (createNew) {
			result.writeToFile(configFile);
		}

		return result;
	}
}
