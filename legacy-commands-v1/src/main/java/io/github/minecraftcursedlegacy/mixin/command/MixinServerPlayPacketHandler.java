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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.minecraftcursedlegacy.api.command.ChatEvent;
import io.github.minecraftcursedlegacy.api.command.CommandDispatchEvent;
import io.github.minecraftcursedlegacy.api.command.ServerCommandUtils;
import io.github.minecraftcursedlegacy.api.event.ActionResult;
import net.minecraft.packet.play.ChatMessagePacket;
import net.minecraft.server.network.ServerPlayPacketHandler;

@Mixin(ServerPlayPacketHandler.class)
public abstract class MixinServerPlayPacketHandler {
	@Inject(method = "method_836", at = @At("HEAD"), cancellable = true)
	void handleCommand(String string, CallbackInfo ci) {
		if (CommandDispatchEvent.INSTANCE.invoker().onDispatch(ServerCommandUtils.createSender((ServerPlayPacketHandler) (Object) this), string.substring(1))) {
			ci.cancel();
		}
	}

	@Inject(method = "handleChatMessage", at = @At(value = "INVOKE", target = "Ljava/lang/String;startsWith(Ljava/lang/String;)Z", remap = false), cancellable = true)
	private void onChatMessageReceived(ChatMessagePacket packet, CallbackInfo info) {
		if (ChatEvent.MULTIPLAYER.invoker().onMessageSent(ServerCommandUtils.createSender((ServerPlayPacketHandler) (Object) this), packet.message.trim()) == ActionResult.FAIL) {
			info.cancel();
		}
	}
}
