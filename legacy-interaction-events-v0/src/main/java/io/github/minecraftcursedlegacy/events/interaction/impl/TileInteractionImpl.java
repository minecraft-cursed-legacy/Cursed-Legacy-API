package io.github.minecraftcursedlegacy.events.interaction.impl;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.minecraftcursedlegacy.api.event.ActionResult;
import io.github.minecraftcursedlegacy.api.event.TileInteractionCallback;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.tile.Tile;

public class TileInteractionImpl {
	public static void onTileInteract(Player player, Level level, ItemInstance item, int x, int y, int z, int i1, CallbackInfoReturnable<Boolean> info) {
		int tile = level.getTileId(x, y, z);
		ActionResult result = TileInteractionCallback.EVENT.invoker().onTileInteract(player, level, item, Tile.BY_ID[tile], x, y, z, i1);

		if (result != ActionResult.PASS) {
			info.setReturnValue(result == ActionResult.SUCCESS);
		}
	}
}
