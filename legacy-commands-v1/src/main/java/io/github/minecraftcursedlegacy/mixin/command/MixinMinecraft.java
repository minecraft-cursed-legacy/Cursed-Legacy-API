package io.github.minecraftcursedlegacy.mixin.command;

import io.github.minecraftcursedlegacy.api.command.CommandDispatchEvent;
import io.github.minecraftcursedlegacy.api.command.CursedCommandSource;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
	@Shadow public AbstractClientPlayer player;

	@Shadow public abstract boolean isConnectedToServer();

	@Redirect(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;isConnectedToServer()Z", ordinal = 0))
	private boolean canOpenChatScreen(Minecraft minecraft) {
		return true;
	}

	@Inject(at = @At("HEAD"), method = "handleClientCommand", cancellable = true)
	private void handleClientCommand(String command, CallbackInfoReturnable<Boolean> info) {
		if (command.length() > 1 && command.startsWith("/") && !isConnectedToServer()) {
			command = command.substring(1);
			CommandDispatchEvent.SINGLEPLAYER_DISPATCH.invoker().dispatch(CursedCommandSource.singleplayer(player), command);
			info.setReturnValue(true);
		}
	}
}
