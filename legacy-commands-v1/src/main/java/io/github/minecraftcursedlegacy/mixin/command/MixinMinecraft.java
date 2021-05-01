package io.github.minecraftcursedlegacy.mixin.command;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.minecraftcursedlegacy.api.command.DispatcherRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.level.Level;

@Mixin(Minecraft.class)
public class MixinMinecraft {
	@Redirect(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;hasLevel()Z", ordinal = 0))
	private boolean canOpenChatScreen(Minecraft minecraft) {
		Level level = ((Minecraft) (Object) this).level;
		return level != null && (DispatcherRegistry.hasSingleplayerCommands() || level.isClient);
	}

	@Inject(at = @At("HEAD"), method = "handleClientCommand", cancellable = true)
	private void handleClientCommand(String command, CallbackInfoReturnable<Boolean> info) {
		if (command.length() > 1 && command.startsWith("/") && !((Minecraft) (Object) this).isConnectedToServer()) {
			command = command.substring(1);
			String commandName = command.split(" ")[0];
			DispatcherRegistry.dispatch(((Minecraft) (Object) this).player, commandName, command, true);
			info.setReturnValue(true);
		}
	}
}
