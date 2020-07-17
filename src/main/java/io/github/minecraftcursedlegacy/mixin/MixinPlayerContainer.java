package io.github.minecraftcursedlegacy.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.player.PlayerContainer;
import net.minecraft.item.ItemInstance;

@Mixin(PlayerContainer.class)
public class MixinPlayerContainer {
	@Inject(at = @At("HEAD"), method = "transferSlot")
	private void transferSlot(int i, CallbackInfoReturnable<ItemInstance> yeet) {
		System.out.println(randeeee.nextInt());
	}

	private static final Random randeeee = new Random();
}
