package io.github.minecraftcursedlegacy.testmixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.player.Player;

@Mixin(Player.class)
public class MixinPlayer {
//	@Inject(at = @At("HEAD"), method = "dropItem(Lnet/minecraft/item/ItemInstance;Z)V")
//	private void dropItem(ItemInstance arg, boolean flag, CallbackInfo info) {
//		System.out.println(DataManager.ITEM_INSTANCE.getAttachedData(arg, ItemDataTest.test_axe).tile);
//	}
}
