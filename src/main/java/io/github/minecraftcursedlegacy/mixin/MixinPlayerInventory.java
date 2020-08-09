package io.github.minecraftcursedlegacy.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import io.github.minecraftcursedlegacy.api.data.AttachedData;
import io.github.minecraftcursedlegacy.api.registry.Id;
import io.github.minecraftcursedlegacy.impl.data.DataStorage;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemInstance;

@Mixin(PlayerInventory.class)
public class MixinPlayerInventory {
	@Redirect(method = "method_685", at = @At(value = "NEW", target = "net/minecraft/item/ItemInstance"))
	private ItemInstance injectForItemPickup(int id, int count, int damage, ItemInstance param) {
		ItemInstance result = new ItemInstance(id, count, damage);
		DataStorage resultt = (DataStorage) (Object) result;

		((DataStorage) (Object) param).getAllAttachedData().forEach(entry -> {
			Id dataId = entry.getKey();
			AttachedData data = entry.getValue();
			resultt.putAttachedData(dataId, data.copy());
		});

		return result;
	}
}
