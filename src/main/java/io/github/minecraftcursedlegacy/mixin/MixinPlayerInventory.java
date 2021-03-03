package io.github.minecraftcursedlegacy.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import io.github.minecraftcursedlegacy.api.data.DataManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemInstance;

@Mixin(PlayerInventory.class)
public class MixinPlayerInventory {
	@Redirect(method = "method_685", at = @At(value = "NEW", target = "net/minecraft/item/ItemInstance"))
	private ItemInstance api_injectForItemPickup(int id, int count, int damage, ItemInstance param) {
		ItemInstance result = new ItemInstance(id, count, damage);
		DataManager.ITEM_INSTANCE.copyData(param, result);
		return result;
	}
}
