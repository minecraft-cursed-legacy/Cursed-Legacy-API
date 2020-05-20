package io.github.minecraftcursedlegacy.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.minecraftcursedlegacy.impl.client.TextureRegistryImpl;
import net.minecraft.client.render.Tessellator;

@Mixin(Tessellator.class)
public class MixinTessellator {
	@Inject(at = @At("HEAD"), method = "vertex", cancellable = true)
	public void vertex(double d, double d1, double d2, double d3, double d4, CallbackInfo bruh) {
		if (TextureRegistryImpl.vertexHacksEnabled) {
			Double texture1 = null;
			Double texture2 = null;

			int var4 = TextureRegistryImpl.var4hack;
			float var5 = ((float)(var4 % 16 * 16) + 0.0F) / 256.0F;
			float var6 = ((float)(var4 % 16 * 16) + 15.99F) / 256.0F;
			float var6_2 = ((float)(var4 % 16 * 16) + 16F) / 256.0F;
			float var7 = ((float)(var4 / 16 * 16) + 0.0F) / 256.0F;
			float var8 = ((float)(var4 / 16 * 16) + 15.99F) / 256.0F;
			float var8_2 = ((float)(var4 / 16 * 16) + 16F) / 256.0F;

			if (d3 == (double)var6 || d3 == (double)var6_2) {
				texture1 = 1D;
			} else if (d3 == (double)var5) {
				texture1 = 0D;
			}

			if (d4 == (double)var8 || d4 == (double)var8_2) {
				texture2 = 1D;
			} else if (d4 == (double)var7) {
				texture2 = 0D;
			}

			if (d2 == 0F) {
				d2 = -0.03125F;
			}
			
			if (d2 == -0.0625F) {
				d2 = -0.03125F;
			}
			if (texture1 != null && texture2 != null) {
				((Tessellator)(Object)this).size(texture1, texture2); //Actually Texture Cords
				((Tessellator)(Object)this).pos(d, d1, d2);
			}
			bruh.cancel();
		}
	}
}