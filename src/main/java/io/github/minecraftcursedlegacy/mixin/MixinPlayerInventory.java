package io.github.minecraftcursedlegacy.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.player.PlayerInventory;

@Mixin(PlayerInventory.class)
public class MixinPlayerInventory {
	@Redirect(method = "method_685")
	private void e() {
		
	}
}
