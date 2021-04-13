package io.github.minecraftcursedlegacy.api.attacheddata.v1;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import io.github.minecraftcursedlegacy.api.registry.Id;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.LevelProperties;
import net.minecraft.util.io.CompoundTag;

/**
 * Manager for data which can be attached to various vanilla objects, such as items and levels.
 */
public final class DataManager<T> extends io.github.minecraftcursedlegacy.api.data.DataManager<T> {
	private final Map<Id, Function<T, ? extends AttachedData>> attachedDataFactories = new HashMap<>();

	/**
	 * Adds the specified attached data to the {@link DataManager} instance. This data can later be accessed on an instance of the object via {@link #getAttachedData}.
	 * @return a key to use to retrieve the attached data from an object.
	 */
	@Override
	public <E extends AttachedData> DataKey<E> addAttachedData(Id id, Function<T, E> dataProvider) {
		this.attachedDataFactories.put(id, dataProvider);
		return new DataKey<>(id);
	}

	/**
	 * Retrieves the specified attached data from the object.
	 */
	public <E extends AttachedData> E getAttachedData(T object, DataKey<E> key) throws ClassCastException {
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
	public AttachedData deserialize(T object, Id id, CompoundTag data) {
		AttachedData result = this.attachedDataFactories.get(id).apply(object);
		result.fromTag(data);
		return result;
	}

	/**
	 * Used by the implementation.
	 * @param from the object to use the data of.
	 * @param to the object to receive the data.
	 */
	public void copyData(T from, T to) {
		DataStorage to_ = (DataStorage) to;

		((DataStorage) from).getAllAttachedData().forEach(entry -> {
			Id dataId = entry.getKey();
			AttachedData data = (AttachedData) entry.getValue();
			to_.putAttachedData(dataId, data.copy());
		});
	}

	/**
	 * Used by the implementation. 
	 * @param object the object which is storing the data. The class thereof must implement {@linkplain DataStorage} or have a mixin implementing it on the class. This is assumed to be the latter as this api exists specifically for game classes that do not have useful data attachment interfaces.
	 * @param moddedTag the modded nbt tag which stores existing data.
	 * @since 1.0.0
	 * @throws ClassCastException when you didn't make sure the object is implementing DataStorage through a mixin.
	 */
	public void loadData(T object, CompoundTag moddedTag) throws ClassCastException {
		this.getDataKeys().forEach(id -> {
			if (moddedTag.containsKey(id.toString())) {
				((DataStorage) object).putAttachedData(id, this.deserialize(object, id, moddedTag.getCompoundTag(id.toString())));
			}
		});
	}

	public static final DataManager<ItemInstance> ITEM_INSTANCE = new DataManager<>();
	
	/**
	 * @since 1.0.0
	 */
	public static final DataManager<LevelProperties> LEVEL_PROPERTIES = new DataManager<>();

	static {
		io.github.minecraftcursedlegacy.api.data.DataManager.ITEM_INSTANCE = ITEM_INSTANCE;
	}

	/**
	 * Id class that is generically bound to a subclass of attached data, for ease of access.
	 */
	public static final class DataKey<T extends AttachedData> extends io.github.minecraftcursedlegacy.api.data.DataManager.DataKey<T> {
		private DataKey(Id id) throws NullPointerException {
			super(id);

			if (id == null) {
				throw new NullPointerException("DataKey cannot store a null ID!");
			}

			this.id = id;
		}

		private final Id id;

		@SuppressWarnings("unchecked")
		private T apply(AttachedData data) throws ClassCastException {
			return (T) data;
		}
	}
}
