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
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import io.github.minecraftcursedlegacy.api.terrain.ChunkGenerator;
import io.github.minecraftcursedlegacy.impl.terrain.InternalLevelSourceAccess;
import net.minecraft.level.Level;
import net.minecraft.level.source.LevelSource;

@Mixin(Level.class)
public class MixinLevel {
	@ModifyConstant(constant = @Constant(intValue = 63), method = "method_152")
	public int alterMinY(int original) {
		Level self = (Level) (Object) this;
		LevelSource cg = ((InternalLevelSourceAccess) self.dimension).getInternalLevelSource();

		if (cg instanceof ChunkGenerator) {
			return ((ChunkGenerator) cg).getMinSpawnY();
		}

		return original;
	}
}
