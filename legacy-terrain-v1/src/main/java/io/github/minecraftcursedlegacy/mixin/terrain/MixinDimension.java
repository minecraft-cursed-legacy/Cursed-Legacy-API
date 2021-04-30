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

package io.github.minecraftcursedlegacy.mixin.terrain;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.minecraftcursedlegacy.api.terrain.ChunkGenerator;
import io.github.minecraftcursedlegacy.impl.terrain.InternalLevelSourceSetter;
import net.minecraft.level.dimension.Dimension;
import net.minecraft.level.source.LevelSource;

@Mixin(Dimension.class)
public class MixinDimension implements InternalLevelSourceSetter {
	private LevelSource api_level_source;

	@Inject(at = @At("HEAD"), method = "method_1770", cancellable = true)
	private void onIsValidSpawnPos(int x, int z, CallbackInfoReturnable<Boolean> info) {
		if (this.api_level_source instanceof ChunkGenerator) {
			info.setReturnValue(((ChunkGenerator) this.api_level_source).isValidSpawnPos(x, z));
		}
	}

	// This is better than manually making an accessor and case for every cache
	@Inject(at = @At("RETURN"), method = "createLevelSource")
	private void onCreateLevelSource(CallbackInfoReturnable<LevelSource> info) {
		this.setInternalLevelSource(info.getReturnValue());
	}

	@Override
	public LevelSource setInternalLevelSource(LevelSource source) {
		return this.api_level_source = source;
	}
}
