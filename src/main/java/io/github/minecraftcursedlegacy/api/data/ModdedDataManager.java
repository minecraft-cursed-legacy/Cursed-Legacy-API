package io.github.minecraftcursedlegacy.api.data;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.github.minecraftcursedlegacy.api.registry.Id;
import io.github.minecraftcursedlegacy.impl.data.DataStorage;

/**
 * Manager for data which can be attached to various vanilla objects, such as items and blocks. See specific implementations for more details.
 */
public final class ModdedDataManager<T> {
	private ModdedDataManager() {
	}

	private final Map<Id, Function<T, ? extends ModdedData>> moddedDataFactories = new HashMap<>();

	public <E extends ModdedData> ModdedDataKey<E> addModdedData(Id id, Function<T, E> dataProvider) {
		this.moddedDataFactories.put(id, dataProvider);
		return new ModdedDataKey<>(id);
	}

	public <E extends ModdedData> E getModdedData(T object, ModdedDataKey<E> id) throws ClassCastException {
		return id.apply(((DataStorage) object).getModdedData(id.id, () -> this.moddedDataFactories.get(id.id).apply(object)));
	}

	public static final ModdedDataManager ITEM_INSTANCE = new ModdedDataManager<>();

	public static final class ModdedDataKey<T extends ModdedData> {
		private ModdedDataKey(Id id) {
			this.id = id;
		}

		private final Id id;

		@SuppressWarnings("unchecked")
		private T apply(ModdedData data) throws ClassCastException {
			return (T) data;
		}
	}
}
