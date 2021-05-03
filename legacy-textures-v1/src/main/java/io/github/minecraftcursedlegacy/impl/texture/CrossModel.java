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

import paulevs.corelib.model.Model;
import paulevs.corelib.model.shape.Shape;
import paulevs.corelib.model.shape.ShapeCross;
import paulevs.corelib.texture.UVPair;

public class CrossModel extends Model {
	public CrossModel(String texture) {
		this.addTexture("texture", texture);
	}

	@Override
	public void renderBlock() {
		render();
	}

	@Override
	public void renderItem() {
		render();
	}

	private void render() {
		Shape.setUV(getBlockUV());
		CROSS.render();
	}

	@Override
	public boolean hasItem() {
		return true;
	}

	@Override
	public UVPair particleUV() {
		return getBlockUV();
	}

	private UVPair getBlockUV() {
		return this.getUV("texture");
	}

	private static final Shape CROSS = new ShapeCross();
}
