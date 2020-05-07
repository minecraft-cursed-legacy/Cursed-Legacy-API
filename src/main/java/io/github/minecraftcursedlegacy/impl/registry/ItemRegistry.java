package io.github.minecraftcursedlegacy.impl.registry;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.IntFunction;

import io.github.minecraftcursedlegacy.accessor.AccessorRecipeRegistry;
import io.github.minecraftcursedlegacy.accessor.AccessorShapedRecipe;
import io.github.minecraftcursedlegacy.accessor.AccessorShapelessRecipe;
import io.github.minecraftcursedlegacy.accessor.AccessorTileItem;
import io.github.minecraftcursedlegacy.api.registry.Id;
import io.github.minecraftcursedlegacy.api.registry.Registry;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.item.TileItem;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeRegistry;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.tile.Tile;

class ItemTypeRegistry extends Registry<ItemType> {
	ItemTypeRegistry(Id registryName) {
		super(ItemType.class, registryName, null);

		// add vanilla item types
		for (int i = 0; i < ItemType.byId.length; ++i) {
			ItemType value = ItemType.byId[i];

			if (value instanceof TileItem) {
				RegistryImpl.T_2_TI.put(Tile.BY_ID[((AccessorTileItem) value).getTileId()], (TileItem) value);
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
		return super.register(id, rawSID -> valueProvider.apply(rawSID - 256));
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
	protected int getStartSerialisedId() {
		return 1;
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
					((IdSetter) recipe).setId(newId);
				}
			}
		}
	}

	TileItem addTileItem(Id id, Tile tile, BiFunction<Integer, Tile, TileItem> constructor) {
		TileItem item = constructor.apply(RegistryImpl.nextTileItemId() - 256, tile);
		this.byRegistryId.put(id, item);
		this.bySerialisedId.put(item.id, item);
		return item;
	}
}