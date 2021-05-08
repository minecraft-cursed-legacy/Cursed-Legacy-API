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

package io.github.minecraftcursedlegacy.api.registry;

import javax.annotation.Nullable;

/**
 * Interface look at the difference between a registry before and after remapping.
 * @since 1.1.0
 */
public interface RegistryDiff<T> {
	/**
	 * Retrieves the new serialised id by the old one.
	 * @param old the old serialised id.
	 * @return the new serialised id, if present
	 */
	@Nullable
	Integer getNewSerialisedId(int old);
	/**
	 * Retrieves the object associated with an old serialised id.
	 * @param old the old serialised id.
	 * @return the object that was associated with it.
	 */
	@Nullable
	T getByOldSerialisedId(int old);
	/**
	 * Retrieves the old serialised id that was associated with an object.
	 * @param value the object to retrieve the id for.
	 * @return the old serialised id that was associated with an object.
	 */
	int getOldSerialisedId(T value);
}
