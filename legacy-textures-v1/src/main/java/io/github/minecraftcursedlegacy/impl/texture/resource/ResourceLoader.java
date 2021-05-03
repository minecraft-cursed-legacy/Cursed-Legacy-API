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
import java.util.function.Supplier;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;

import com.google.gson.Gson;

import io.github.minecraftcursedlegacy.api.registry.Id;

/**
 * Resource Loader for models and textures.
 */
public class ResourceLoader {
	/**
	 * Retrieves the resource specified as a JModel.
	 * @param id the resource id.
	 * @param type the subfile type. item or tile usually.
	 * @return the loaded model object.
	 */
	public static JModel getModel(Id id, String type) {
		InputStream stream = getStream(id, "models/" + type, ".json");
		return stream == null ? createDefaultModel(id, type) : GSON.fromJson(
				new InputStreamReader(stream),
				JModel.class);
	}

	/**
	 * Retrieves the resource specified as a BufferedImage, caching results.
	 * @param id the resource id.
	 * @param type the subfile type. item or tile usually.
	 * @return the respective buffered image.
	 */
	@Nullable
	public static BufferedImage getTexture(Id id, String type) {
		return TEXTURES.computeIfAbsent(type, t -> new HashMap<>()).computeIfAbsent(id, id_ -> {
			try {
				return ImageIO.read(getURL(id, "textures/" + type, ".png"));
			} catch (IOException e) {
				throw new UncheckedIOException("Execption loading texture", e);
			}
		});
	}

	@Nullable
	public static String getValidatedTextureLocation(Id provided) {
		String resourceLocation = getTextureLocation(provided, ".png");

		// validate
		if (ResourceLoader.class.getClassLoader().getResource(resourceLocation) == null) {
			return null;
		}

		return resourceLocation;
	}

	public static void addModelSetup(Id id, ModelSetup setup) {
		SETUPS.put(id, setup);
	}

	public static ModelSetup getModelSetup(Id id, Supplier<ModelSetup> ifAbsent) {
		return SETUPS.computeIfAbsent(id, $ -> ifAbsent.get());
	}

	private static JModel createDefaultModel(Id id, String type) {
		boolean tile = type.charAt(0) == 't'; // yotefuckinhaw this is gonna backfire in the future isn't it
		JModel result = new JModel();
		result.parent = tile ? "tile/cube_all" : "item/generated";
		result.textures = new HashMap<>();
		result.textures.put(tile ? "all" : "", id.getNamespace() + ":" + type + "/" + id.getName());
		return result;
	}

	@Nullable
	private static InputStream getStream(Id id, String locator, String extension) {
		return ResourceLoader.class.getClassLoader().getResourceAsStream(getResourceLocation(id, locator, extension));
	}

	@Nullable
	private static URL getURL(Id id, String locator, String extension) {
		return ResourceLoader.class.getClassLoader().getResource(getResourceLocation(id, locator, extension));
	}

	private static String getResourceLocation(Id id, String locator, String extension) {
		return "assets/" + id.getNamespace() + "/" + locator + "/" + id.getName() + extension;
	}

	private static String getTextureLocation(Id id, String extension) {
		return "assets/" + id.getNamespace() + "/textures/" + id.getName() + extension;
	}

	private static final Map<Id, ModelSetup> SETUPS = new HashMap<>();
	private static final Map<String, Map<Id, BufferedImage>> TEXTURES = new HashMap<>();
	private static final Gson GSON = new Gson();
}
