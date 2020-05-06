package io.github.minecraftcursedlegacy.api.registry;

import java.util.function.IntFunction;

import javax.annotation.Nullable;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import io.github.minecraftcursedlegacy.impl.registry.RegistryImpl;
import net.minecraft.item.ItemType;
import net.minecraft.tile.Tile;

/**
 * Registry for game content.
 */
public class Registry<T> {
	/**
	 * Creates a new registry object.
	 * @param clazz the class of the values in this registry.
	 * @param registryName the identifier for this registry.
	 * @param defaultValue the default value for ids in registry.
	 */
	public Registry(Class<T> clazz, Id registryName, @Nullable T defaultValue) {
		this.registryName = registryName;
		this.defaultValue = defaultValue;
		RegistryImpl.addRegistry(this);
	}

	protected final BiMap<Id, T> byRegistryId = HashBiMap.create();
	protected final BiMap<Integer, T> bySerialisedId = HashBiMap.create();
	private final Id registryName;
	@Nullable
	private final T defaultValue;
	private int nextId = 0;
	/**
	 * Whether the registry is locked, and values can no longer be registered to it.
	 */
	protected boolean locked = false;

	/**
	 * Registers a value to the registry.
	 * @param id the id of the value to register to the registry.
	 * @param value the value to register to the registry.
	 * @return the value registered to the registry.
	 */
	public <E extends T> E register(Id id, E value) {
		if (locked) {
			throw new RuntimeException("Registry is locked!");
		}

		int serialisedId = this.getNextSerialisedId();
		this.byRegistryId.put(id, value);
		this.bySerialisedId.put(serialisedId, value);
		this.onRegister(serialisedId, id, value);
		return value;
	}

	/**
	 * Registers a value to the registry, with the serialised id supplied.
	 * @param id the id of the value to register to the registry.
	 * @param valueProvider the provider of the value to register to the registry, which takes the int serialised id.
	 * @return the value registered to the registry.
	 */
	public <E extends T> E register(Id id, IntFunction<E> valueProvider) {
		if (locked) {
			throw new RuntimeException("Registry is locked!");
		}

		int serialisedId = this.getNextSerialisedId();
		E value = valueProvider.apply(serialisedId);
		this.byRegistryId.put(id, value);
		this.bySerialisedId.put(serialisedId, value);
		this.onRegister(serialisedId, id, value);
		return value;
	}

	/**
	 * Called when a value is registered.
	 * @param serialisedId the serialised id of the registered value.
	 * @param id the registry id of the registered value.
	 * @param value the registered value.
	 */
	protected void onRegister(int serialisedId, Id id, T value) {
	}

	/**
	 * Looks up the id in the registy.
	 * @param id the specified id to look up in the registry.
	 * @return the value specified by the id in the registry, if it exists. Otherwise returns the default value.
	 */
	@Nullable
	public T getById(Id id) {
		return this.byRegistryId.getOrDefault(id, this.defaultValue);
	}

	/**
	 * Looks up the id of the value in the registy.
	 * @param id the specified id to look up in the registry.
	 * @return the id of the value in the registry, if it exists. Otherwise returns null.
	 */
	@Nullable
	public Id getId(T value) {
		return this.byRegistryId.inverse().get(value);
	}

	/**
	 * Looks up the int serialised id in the registy.
	 * @param serialisedId the specified serialised id to look up in the registry.
	 * @return the value specified by the serialised id in the registry, if it exists. Otherwise returns the default value.
	 */
	public T getBySerialisedId(int serialisedId) {
		return this.bySerialisedId.getOrDefault(serialisedId, this.defaultValue);
	}

	/**
	 * Looks up the int serialised id of the value in the registy.
	 * @param id the specified serialised id to look up in the registry.
	 * @return the int serialised id of the value in the registry, if it exists. Otherwise returns null.
	 */
	public int getSerialisedId(T value) {
		return this.bySerialisedId.inverse().get(value);
	}

	/**
	 * @return the identifier of this registry.
	 */
	public final Id getRegistryName() {
		return this.registryName;
	}

	/**
	 * @return the next serialised id to use.
	 */
	protected int getNextSerialisedId() {
		return this.nextId++;
	}

	/**
	 * Locks all registries. Used by the implementation.
	 */
	public static void lockAll() {
		RegistryImpl.registries().forEach(r -> r.locked = true);
	}

	/**
	 * Registry for Item Types.
	 */
	public static Registry<ItemType> ITEM_TYPE = RegistryImpl.createItemTypeRegistry(new Id("api:item_type"));
	/**
	 * Registry for Tiles. Automatically adds an associated item type.
	 */
	public static Registry<Tile> TILE = RegistryImpl.createTileRegistry(new Id("api:tile"));
}
