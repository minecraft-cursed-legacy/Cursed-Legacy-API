/*
 * Copyright (c) 2020 The Cursed Legacy Team.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package io.github.minecraftcursedlegacy.impl.texture.resource;

import java.util.HashMap;

import io.github.minecraftcursedlegacy.api.registry.Id;
import io.github.minecraftcursedlegacy.api.registry.Registries;
import io.github.minecraftcursedlegacy.impl.registry.HasParentId;
import net.minecraft.tile.Tile;

public enum ModelType {
	TILE("tile") {
		@Override
		public ModelJson createDefaultModel(Id id) {
			ModelJson result = new ModelJson();

			result.parent = "tile/cube_all";
			result.textures = new HashMap<>();
			result.textures.put("all", this.createLocation(id));
			result.root = ResourceLoader.getSetup(new Id(result.parent));

			if (result.root == null) {
				throw new IllegalStateException("Could not get built in setup for \"" + result.parent + "\"!");
			}

			return result;
		}
	},
	ITEM("item") {
		@Override
		public ModelJson createDefaultModel(Id id) {
			ModelJson result = new ModelJson();

			result.parent = "item/generated";
			result.textures = new HashMap<>();
			result.textures.put("", this.createLocation(id));
			result.root = ResourceLoader.getSetup(new Id(result.parent));

			if (result.root == null) {
				throw new IllegalStateException("Could not get built in setup for \"" + result.parent + "\"!");
			}

			return result;
		}
	},
	TILEITEM("item") {
		@Override
		public ModelJson createDefaultModel(Id id) {
			ModelJson result = new ModelJson();

			// substitute parent id for tile items
			// Step 1: Get the ItemType, guaranteed to be a tile item of some sort
			// Step 2: Get the integer id, and look up the tile it is associated with
			// Step 3: Get the Id for that Tile
			id = Registries.TILE.getId(
				Tile.BY_ID[
					((HasParentId) Registries.ITEM_TYPE.getById(id)).getParentId()
				]
			);

			Id parentId = new Id(id.getNamespace(), "tile/" + id.getName());
			result.parent = parentId.toString();
			ModelJson parent = ResourceLoader.getModelDirect(parentId);
			result.root = parent.root;
			result.textures = parent.textures;

			if (result.root == null) {
				throw new IllegalStateException("Could not get built in setup for \"" + result.parent + "\"!");
			}

			return result;
		}
	};

	private ModelType(String location) {
		this.location = location;
	}

	private final String location;

	public String getLocation() {
		return this.location;
	}

	protected String createLocation(Id id) {
		return id.getNamespace() + ":" + this.getLocation() + "/" + id.getName();
	}

	public abstract ModelJson createDefaultModel(Id id);
}
