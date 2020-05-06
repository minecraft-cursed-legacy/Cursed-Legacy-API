package io.github.minecraftcursedlegacy.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.client.util.ResourceDownloadThread;

@Mixin(ResourceDownloadThread.class)
public class MixinResourceDownloadThread {
	@Redirect(
			at = @At(
					value = "INVOKE",
					target = "java/lang/Exception.printStackTrace()V"
					),
			method = "method_110")
	private void method_110(Exception e) {
		// avoid aggressive spam
	}
}
