package io.github.minecraftcursedlegacy.mixin.command;

import io.github.minecraftcursedlegacy.api.command.CommandDispatchEvent;
import io.github.minecraftcursedlegacy.api.command.CursedCommandSource;
import net.minecraft.server.network.ServerPlayPacketHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayPacketHandler.class)
public abstract class MixinServerPlayPacketHandler {
    @Inject(method = "method_836", at = @At(value = "HEAD"), cancellable = true)
    void handleCommand(String string, CallbackInfo ci) {
        if (CommandDispatchEvent.MULTIPLAYER_DISPATCH.invoker().dispatch(CursedCommandSource.multiplayer((ServerPlayPacketHandler) (Object) this), string.substring(1))) {
            ci.cancel();
        }
    }
}
