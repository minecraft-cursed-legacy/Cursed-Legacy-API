package io.github.minecraftcursedlegacy.mixin.events.interaction;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.minecraftcursedlegacy.impl.events.interaction.TileInteractionImpl;
import net.minecraft.client.ClientInteractionManager;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;

@Mixin(ClientInteractionManager.class)
public class MixinClientInteractionManager {
	@Inject(at = @At("HEAD"), method = "useItemOnTile", cancellable = true)
	private void api_onTileInteract(Player player, Level level, ItemInstance item, int x, int y, int z, int i1, CallbackInfoReturnable<Boolean> info) {
		TileInteractionImpl.onTileInteract(player, level, item, x, y, z, i1, info);
	}
}