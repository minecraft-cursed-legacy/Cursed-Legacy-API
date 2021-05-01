/*
 * Copyright (c) 2020 The Cursed Legacy Team.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package io.github.minecraftcursedlegacy.mixin.command;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.minecraftcursedlegacy.api.command.ChatEvent;
import io.github.minecraftcursedlegacy.api.command.DispatcherRegistry;
import io.github.minecraftcursedlegacy.api.command.Sender;
import io.github.minecraftcursedlegacy.api.event.ActionResult;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.AbstractClientPlayer;
import net.minecraft.level.Level;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
	@Shadow 
	public AbstractClientPlayer player;

	@Shadow
	public Level level;

	@Shadow
	public abstract boolean isConnectedToServer();

	@Redirect(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;isConnectedToServer()Z", ordinal = 0))
	private boolean canOpenChatScreen(Minecraft minecraft) {
		return this.level != null && (DispatcherRegistry.isSingleplayerChatEnabled() || this.level.isClient);
	}

	@Inject(at = @At("HEAD"), method = "handleClientCommand", cancellable = true)
	private void handleClientCommand(String message, CallbackInfoReturnable<Boolean> info) {
		if (ChatEvent.SINGLEPLAYER.invoker().onMessageSent(Sender.fromPlayer(this.player), message) == ActionResult.FAIL) {
			info.setReturnValue(true);
		}
	}
}
