package io.github.minecraftcursedlegacy.api.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import io.github.minecraftcursedlegacy.api.registry.Id;
import io.github.minecraftcursedlegacy.impl.attacheddata.DataStorage;
import net.minecraft.item.ItemInstance;
import net.minecraft.util.io.CompoundTag;

/**
 * Manager for data which can be attached to various vanilla objects, such as items and blocks.
 * @deprecated since 1.0.0. Use {@linkplain io.github.minecraftcursedlegacy.api.attacheddata.v1.DataManager this equivalent} from api v1 instead.
 */
@Deprecated
public class DataManager<T> {
	private final Map<Id, Function<T, ? extends io.github.minecraftcursedlegacy.api.attacheddata.v1.AttachedData>> attachedDataFactories = new HashMap<>();

	/**
	 * Adds the specified attached data to the {@link DataManager} instance. This data can later be accessed on an instance of the object via {@link #getAttachedData}.
	 * @return a key to use to retrieve the attached data from an object.
	 */
	public <E extends io.github.minecraftcursedlegacy.api.attacheddata.v1.AttachedData> DataKey<E> addAttachedData(Id id, Function<T, E> dataProvider) {
		this.attachedDataFactories.put(id, dataProvider);
		return new DataKey<>(id);
	}

	/**
	 * Retrieves the specified attached data from the object.
	 */
	public <E extends io.github.minecraftcursedlegacy.api.attacheddata.v1.AttachedData> E getAttachedData(T object, DataKey<E> key) throws ClassCastException {
		return key.apply(((DataStorage) object).getAttachedData(key.id, () -> this.attachedDataFactories.get(key.id).apply(object)));
	}

	/**
	 * Used by the implementation.
	 * @return a {@linkplain Set set} of all {@linkplain Id ids} of {@link AttachedData} instances registered to this manager.
	 */
	public Set<Id> getDataKeys() {
		return this.attachedDataFactories.keySet();
	}

	/**
	 * Used by the implementation.
	 * @return an attached data instance of the given type constructed by the given tag.
	 */
	public io.github.minecraftcursedlegacy.api.attacheddata.v1.AttachedData deserialize(T object, Id id, CompoundTag data) {
		io.github.minecraftcursedlegacy.api.attacheddata.v1.AttachedData result = this.attachedDataFactories.get(id).apply(object);
		result.fromTag(data);
		return result;
	}

	/**
	 * Used by the implementation.
	 * @param from the object to use the data of.
	 * @param to the object to receive the data.
	 */
	public void copyData(T from, T to) {
		DataStorage to_ = (DataStorage) (Object) to;

		((DataStorage) (Object) from).getAllAttachedData().forEach(entry -> {
			Id dataId = entry.getKey();
			io.github.minecraftcursedlegacy.api.attacheddata.v1.AttachedData data = entry.getValue();
			to_.putAttachedData(dataId, data.copy());
		});
	}

	public static final DataManager<ItemInstance> ITEM_INSTANCE = io.github.minecraftcursedlegacy.api.attacheddata.v1.DataManager.ITEM_INSTANCE;

	/**
	 * @deprecated since 1.0.0. Use {@linkplain io.github.minecraftcursedlegacy.api.attacheddata.v1.DataManager.DataKey this equivalent} from api v1 instead.
	 */
	@Deprecated
	public static class DataKey<T extends io.github.minecraftcursedlegacy.api.attacheddata.v1.AttachedData> {
		protected DataKey(Id id) throws NullPointerException {
			if (id == null) {
				throw new NullPointerException("DataKey cannot store a null ID!");
			}

			this.id = id;
		}

		private final Id id;

		@SuppressWarnings("unchecked")
		private T apply(io.github.minecraftcursedlegacy.api.attacheddata.v1.AttachedData data) throws ClassCastException {
			return (T) data;
		}
	}
}