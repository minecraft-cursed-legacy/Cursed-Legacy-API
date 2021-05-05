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

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;

import com.google.gson.Gson;

import io.github.minecraftcursedlegacy.api.registry.Id;
import io.github.minecraftcursedlegacy.api.registry.Registries;
import io.github.minecraftcursedlegacy.impl.registry.HasParentId;
import net.minecraft.tile.Tile;

/**
 * Resource Loader for models and textures.
 * @since 1.1.0
 */
public class ResourceLoader {
	/**
	 * Retrieves the resource specified as a JModel.
	 * @param id the resource id, including type.
	 * @param type the subfile type. item or tile usually.
	 * @return the loaded model object.
	 */
	public static JModel getModel(Id id, String type) {
		String rawType = type;

		if (type.hashCode() == "tileitem".hashCode()) {
			type = "item";
		}

		Id modelId = new Id(id.getNamespace(), type + "/" + id.getName());
		JModel prelim = getModel(modelId);

		if (prelim == null) {
			MODELS.put(modelId, prelim = createDefaultModel(id, rawType));
		}

		return prelim;
	}

	/**
	 * Retrieves the resource specified as a JModel.
	 * @param id the resource id.
	 * @return the loaded model object.
	 */
	@Nullable
	private static JModel getModel(Id id) {
		return MODELS.computeIfAbsent(id, id_ -> {
			InputStream stream = getStream(id_, "models", ".json");
			JModel result = stream == null ? null : GSON.fromJson( // load from json
					new InputStreamReader(stream),
					JModel.class);

			if (result != null) { // if not null, resolve parent stuff
				// recursive initialisation!
				Id next = new Id(result.parent);
				ModelSetup setup = SETUPS.get(next); // get the model setup to check if it's a root java impl or an intermediary step

				if (setup == null) { // if intermediary, we do ye olde recursive function
					JModel parent = getModel(next);

					// fill in parent values where not set
					for (Map.Entry<String, String> entry : parent.textures.entrySet()) {
						result.textures.putIfAbsent(entry.getKey(), entry.getValue());
					}

					// set model setup
					result.root = setup;
				} else {
					// done!
					result.root = setup;
				}

				if (result.root == null) {
					throw new IllegalStateException("Could not get built in setup for \"" + result.parent + "\"!");
				}
			}

			return result;
		});
	}

	/**
	 * Retrieves the resource specified as a BufferedImage, caching results.
	 * @param id the resource id.
	 * @param type the subfile type. item or tile usually.
	 * @return the respective buffered image.
	 */
	@Nullable
	public static BufferedImage getTexture(Id id) {
		return TEXTURES.computeIfAbsent(id, id_ -> {
			try {
				URL url = getURL(id, "textures", ".png");
				return url == null ? null : ImageIO.read(url);
			} catch (IOException e) {
				throw new UncheckedIOException("Execption loading texture", e);
			}
		});
	}

	@Nullable
	public static String getValidatedTextureLocation(Id provided) {
		String resourceLocation = getResourceLocation(provided, "textures", ".png");

		// validate
		if (ResourceLoader.class.getClassLoader().getResource(resourceLocation) == null) {
			return null;
		}

		return resourceLocation;
	}

	public static ModelSetup addModelSetup(Id id, ModelSetup setup) {
		SETUPS.put(id, setup);
		return setup;
	}

	private static JModel createDefaultModel(Id id, String type) {
		boolean tileitem = type.charAt(0) == 't'; // yotefuckinhaw this is gonna backfire in the future isn't it
		boolean tile = tileitem && type.length() == 4;
		tileitem &= !tile; // cursed boolean nonsense

		JModel result = new JModel();
		
		if (tileitem) { // substitute parent id for tile items
			// Step 1: Get the ItemType, guaranteed to be a tile item of some sort
			// Step 2: Get the integer id, and look up the tile it is associated with
			// Step 3: Get the Id for that Tile
			id = Registries.TILE.getId(
					Tile.BY_ID[
					           ((HasParentId) Registries.ITEM_TYPE.getById(id)).getParentId()
					           ]
							);
		}

		result.parent = tile ? "tile/cube_all" : (tileitem ? new Id(id.getNamespace(), "tile/" + id.getName()).toString() : "item/generated");

		if (tileitem) {
			JModel parent = MODELS.get(new Id(result.parent));
			result.root = parent.root;
			result.textures = parent.textures;
		} else {
			result.textures = new HashMap<>();
			result.textures.put(tile ? "all" : "", id.getNamespace() + ":" + type + "/" + id.getName());
			result.root = SETUPS.get(new Id(result.parent));
		}

		if (result.root == null) {
			throw new IllegalStateException("Could not get built in setup for \"" + result.parent + "\"!");
		}

		return result;
	}

	@Nullable
	private static InputStream getStream(Id id, String locator, String extension) {
		return ResourceLoader.class.getClassLoader().getResourceAsStream(getResourceLocation(id, locator, extension));
	}

	@Nullable
	private static URL getURL(Id id, String locator, String extension) {
		String s = getResourceLocation(id, locator, extension);
		return ResourceLoader.class.getClassLoader().getResource(s);
	}

	private static String getResourceLocation(Id id, String locator, String extension) {
		return "assets/" + id.getNamespace() + "/" + locator + "/" + id.getName() + extension;
	}

	private static final Map<Id, ModelSetup> SETUPS = new HashMap<>();
	private static final Map<Id, JModel> MODELS = new HashMap<>();
	private static final Map<Id, BufferedImage> TEXTURES = new HashMap<>();
	private static final Gson GSON = new Gson();
}
