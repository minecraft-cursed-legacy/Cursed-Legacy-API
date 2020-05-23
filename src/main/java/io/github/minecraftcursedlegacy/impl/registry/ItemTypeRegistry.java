package io.github.minecraftcursedlegacy.impl.registry;

import java.util.List;
import java.util.Map.Entry;
import java.util.function.IntFunction;

import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.item.PlaceableTileItem;
import net.minecraft.item.TileItem;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeRegistry;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.tile.Tile;
import net.minecraft.util.io.CompoundTag;

import io.github.minecraftcursedlegacy.accessor.AccessorPlaceableTileItem;
import io.github.minecraftcursedlegacy.accessor.AccessorRecipeRegistry;
import io.github.minecraftcursedlegacy.accessor.AccessorShapedRecipe;
import io.github.minecraftcursedlegacy.accessor.AccessorShapelessRecipe;
import io.github.minecraftcursedlegacy.accessor.AccessorTileItem;
import io.github.minecraftcursedlegacy.api.registry.Id;
import io.github.minecraftcursedlegacy.api.registry.Registry;

class ItemTypeRegistry extends Registry<ItemType> {
	ItemTypeRegistry(Id registryName) {
		super(ItemType.class, registryName, null);

		// add vanilla item types
		for (int i = 0; i < ItemType.byId.length; ++i) {
			ItemType value = ItemType.byId[i];

			if (value instanceof TileItem) {
				RegistryImpl.T_2_TI.put(Tile.BY_ID[((AccessorTileItem) value).getTileId()], (TileItem) value);
			} else if (value instanceof PlaceableTileItem) {
				RegistryImpl.T_2_TI.put(Tile.BY_ID[((AccessorPlaceableTileItem) value).getTileId()], (PlaceableTileItem) value);
			}

			if (value != null) {
				String idPart = value.getTranslationKey();

				if (idPart == null) {
					idPart = "itemtype";
				} else {
					idPart = idPart.substring(5);
				}

				this.byRegistryId.put(new Id(idPart + "_" + i), value);
				this.bySerialisedId.put(i, value);
			}
		}
	}

	private ItemType[] oldItemTypes = new ItemType[ItemType.byId.length];

	@Override
	public <E extends ItemType> E registerValue(Id id, E value) {
		throw new UnsupportedOperationException("Use register(Id, IntFunction<ItemType>) instead, since item types need to use the provided int serialised ids in their constructor!");
	}

	/**
	 * Item Types are weird.
	 */
	@Override
	public <E extends ItemType> E register(Id id, IntFunction<E> valueProvider) {
		return super.register(id, rawSID -> valueProvider.apply(rawSID - Tile.BY_ID.length));
	}

	@Override
	protected int getNextSerialisedId() {
		return RegistryImpl.nextItemTypeId();
	}

	@Override
	protected void beforeRemap() {
		int size = ItemType.byId.length;
		// copy old array for later recipe remapping
		System.arraycopy(ItemType.byId, 0, this.oldItemTypes, 0, size);
		// clear array
		System.arraycopy(new ItemType[size], 0, ItemType.byId, 0, size);
	}

	@Override
	protected void onRemap(ItemType remappedValue, int newSerialisedId) {
		ItemType.byId[newSerialisedId] = remappedValue;
		((IdSetter) remappedValue).setId(newSerialisedId);
	}

	@Override
	protected void postRemap() {
		// Remap Recipes
		RegistryRemapper.LOGGER.info("Remapping recipes.");

		for (Recipe recipe : ((AccessorRecipeRegistry) RecipeRegistry.getInstance()).getRecipes()) {
			if (recipe instanceof ShapedRecipe) {
				// remap recipe ingredients
				ItemInstance[] ingredients = ((AccessorShapedRecipe) recipe).getIngredients();

				for (int i = 0; i < ingredients.length; ++i) {
					ItemInstance instance = ingredients[i];

					if (instance != null) {
						int oldId = instance.itemId;
						int newId = this.oldItemTypes[oldId].id;

						// only remap if necessary
						if (oldId != newId) {
							// set new id
							instance.itemId = newId;
						}
					}
				}

				// recompute output id
				ItemInstance result = ((AccessorShapedRecipe) recipe).getOutput();
				int newId = this.oldItemTypes[result.itemId].id;

				// only remap if necessary
				if (result.itemId != newId) {
					result.itemId = newId;
					((IdSetter) recipe).setId(newId);
				}
			} else if (recipe instanceof ShapelessRecipe) {
				// remap recipe ingredients
				List<ItemInstance> ingredients = ((AccessorShapelessRecipe) recipe).getInput();

				for (ItemInstance instance : ingredients) {
					if (instance != null) {
						int oldId = instance.itemId;
						int newId = this.oldItemTypes[oldId].id;

						// only remap if necessary
						if (oldId != newId) {
							// set new id
							instance.itemId = newId;
						}
					}
				}

				// recompute output id
				ItemInstance result = ((AccessorShapelessRecipe) recipe).getOutput();
				int newId = this.oldItemTypes[result.itemId].id;

				// only remap if necessary
				if (result.itemId != newId) {
					result.itemId = newId;
				}
			}
		}
	}

	@Override
	protected void addNewValues(List<Entry<Id, ItemType>> unmapped, CompoundTag tag) {
		int serialisedTileId = 1;
		int serialisedItemId = Tile.BY_ID.length;

		for (Entry<Id, ItemType> entry : unmapped) {
			ItemType value = entry.getValue();

			if (value instanceof TileItem) {
				while (this.bySerialisedId.get(serialisedTileId) != null) {
					++serialisedTileId;
				}

				// readd to registry
				this.bySerialisedId.put(serialisedTileId, value);
				// add to tag
				tag.put(entry.getKey().toString(), serialisedTileId);
				this.onRemap(value, serialisedTileId);
			} else {
				while (this.bySerialisedId.get(serialisedItemId) != null) {
					++serialisedItemId;
				}

				// readd to registry
				this.bySerialisedId.put(serialisedItemId, value);
				// add to tag
				tag.put(entry.getKey().toString(), serialisedItemId);
				this.onRemap(value, serialisedItemId);
			}
		}
	}

	<I extends ItemType> I addTileItem(Id id, Tile tile, IntFunction<I> constructor) {
		I item = constructor.apply(tile.id - Tile.BY_ID.length);
		this.byRegistryId.put(id, item);
		this.bySerialisedId.put(item.id, item);
		return item;
	}
}