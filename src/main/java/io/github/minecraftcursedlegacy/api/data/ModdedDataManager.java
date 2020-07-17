package io.github.minecraftcursedlegacy.api.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import io.github.minecraftcursedlegacy.api.registry.Id;
import io.github.minecraftcursedlegacy.impl.data.DataStorage;
import net.minecraft.item.ItemInstance;
import net.minecraft.util.io.CompoundTag;

/**
 * Manager for data which can be attached to various vanilla objects, such as items and blocks.
 */
public final class ModdedDataManager<T> {
	private ModdedDataManager() {
	}

	private final Map<Id, Function<T, ? extends ModdedData>> moddedDataFactories = new HashMap<>();

	/**
	 * Adds the specified modded data to the manager instance. This data can later be accessed on an instance of the object via {@link #getModdedData}.
	 * @return a key to use to retrieve the modded data from an object.
	 */
	public <E extends ModdedData> ModdedDataKey<E> addModdedData(Id id, Function<T, E> dataProvider) {
		this.moddedDataFactories.put(id, dataProvider);
		return new ModdedDataKey<>(id);
	}

	/**
	 * Retrieves the specified modded data from the object.
	 */
	public <E extends ModdedData> E getModdedData(T object, ModdedDataKey<E> id) throws ClassCastException {
		return id.apply(((DataStorage) object).getModdedData(id.id, () -> this.moddedDataFactories.get(id.id).apply(object)));
	}

	/**
	 * Used by the implementation.
	 * @return a {@linkplain Set set} of all {@linkplain Id ids} of {@link ModdedData} instances registered to this manager.
	 */
	public Set<Id> getDataKeys() {
		return this.moddedDataFactories.keySet();
	}

	/**
	 * Used by the implementation.
	 * @return a modded data instance of the given type constructed by the given tag.
	 */
	public ModdedData deserialize(T object, Id id, CompoundTag data) {
		ModdedData result = this.moddedDataFactories.get(id).apply(object);
		result.fromTag(data);
		return result;
	}

	public static final ModdedDataManager<ItemInstance> ITEM_INSTANCE = new ModdedDataManager<>();

	public static final class ModdedDataKey<T extends ModdedData> {
		private ModdedDataKey(Id id) throws NullPointerException {
			if (id == null) {
				throw new NullPointerException("ModdedDataKey cannot store a null ID!");
			}

			this.id = id;
		}

		private final Id id;

		@SuppressWarnings("unchecked")
		private T apply(ModdedData data) throws ClassCastException {
			return (T) data;
		}
	}
}
