package io.github.minecraftcursedlegacy.impl.worldtype;

import java.util.ArrayList;
import java.util.List;

import io.github.minecraftcursedlegacy.api.attacheddata.v1.DataManager;
import io.github.minecraftcursedlegacy.api.attacheddata.v1.DataManager.DataKey;
import io.github.minecraftcursedlegacy.api.registry.Translations;
import io.github.minecraftcursedlegacy.api.worldtype.WorldType;
import net.fabricmc.api.ModInitializer;

public class WorldTypeImpl implements ModInitializer {
	@Override
	public void onInitialize() {
		// Create world type attached data
		worldTypeData = DataManager.LEVEL_PROPERTIES.addAttachedData(WorldTypeData.ID, properties -> new WorldTypeData(getSelected().getId()));

		// Translate default
		Translations.addTranslation(WorldType.DEFAULT.toString(), "Default");
	}

	public static DataKey<WorldTypeData> worldTypeData;
	private static final List<WorldType> TYPES = new ArrayList<>();
	private static int selected;
	
	public static void add(WorldType type) {
		TYPES.add(type);
	}
	public static WorldType cycle() {
		if (++selected >= TYPES.size()) {
			selected = 0;
		}

		return TYPES.get(selected);
	}
	
	public static WorldType getSelected() {
		return TYPES.get(selected);
	}
}
