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

package io.github.minecraftcursedlegacy.impl.texture;

import java.awt.image.BufferedImage;

import io.github.minecraftcursedlegacy.api.registry.Id;
import io.github.minecraftcursedlegacy.api.registry.Registries;
import io.github.minecraftcursedlegacy.impl.registry.HasParentId;
import io.github.minecraftcursedlegacy.impl.texture.resource.JModel;
import io.github.minecraftcursedlegacy.impl.texture.resource.ModelSetup;
import io.github.minecraftcursedlegacy.impl.texture.resource.ResourceLoader;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.item.ItemType;
import net.minecraft.tile.Tile;
import paulevs.corelib.model.prefab.FullCubeModel;
import paulevs.corelib.registry.ModelRegistry;

public class ModelDiscoverer implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ResourceLoader.addModelSetup(new Id("item/generated"), (id, obj, data) -> {
			BufferedImage image = ResourceLoader.getTexture(new Id(data.textures.get("")));

			if (image != null) {
				ItemType item = (ItemType) obj;
				item.setTexturePosition(AtlasMapper.registerDefaultSprite(((ItemType) item).id, image)); // idk if the root slash is necessary but it exists in corelib's examples
			}
		});

		ResourceLoader.addModelSetup(new Id("tile/cube_all"), (id, obj, data) -> {
			String image = getValidatedTextureLocation(data.textures.get("all"));

			if (image != null) {
				if (obj instanceof ItemType) {
					ItemType item = (ItemType) obj;
					ModelRegistry.addItemModel(item, new FullCubeModel("/" + image)); // idk if the root slash is necessary but it exists in corelib's examples
				} else {
					Tile tile = (Tile) obj;
					ModelRegistry.addTileModel(tile, new FullCubeModel("/" + image)); // idk if the root slash is necessary but it exists in corelib's examples
				}
			}
		});

		ResourceLoader.addModelSetup(new Id("tile/cross"), (id, obj, data) -> {
			String image = getValidatedTextureLocation(data.textures.get("cross"));

			if (image != null) {
				if (obj instanceof ItemType) {
					ItemType item = (ItemType) obj;
					ModelRegistry.addItemModel(item, new CrossModel("/" + image)); // idk if the root slash is necessary but it exists in corelib's examples
				} else {
					Tile tile = (Tile) obj;
					ModelRegistry.addTileModel(tile, new CrossModel("/" + image)); // idk if the root slash is necessary but it exists in corelib's examples
				}
			}
		});

		Registries.TILE.forEach((id, tile) -> {
			if (tile != null) {
				JModel model = ResourceLoader.getModel(id, "tile");
				model.root.setupModel(id, tile, model);
			}
		});

		Registries.ITEM_TYPE.forEach((id, item) -> {
			if (item != null) {
				JModel model = ResourceLoader.getModel(id, (item instanceof HasParentId) ? "tileitem" : "item");
				model.root.setupModel(id, item, model);
			}
		});
	}

	private static String getValidatedTextureLocation(String provided) {
		return provided == null ? null : ResourceLoader.getValidatedTextureLocation(new Id(((String) provided)));
	}
}
