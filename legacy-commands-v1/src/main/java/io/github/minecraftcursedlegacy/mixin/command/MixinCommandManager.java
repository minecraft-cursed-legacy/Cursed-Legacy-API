package io.github.minecraftcursedlegacy.mixin.command;

import io.github.minecraftcursedlegacy.api.command.CommandDispatchEvent;
import io.github.minecraftcursedlegacy.api.command.CursedCommandSource;
import net.minecraft.server.command.Command;
import net.minecraft.server.command.CommandManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CommandManager.class)
public abstract class MixinCommandManager {
    @Inject(method = "processCommand", at = @At(value = "HEAD"), cancellable = true)
    void handleCommand(Command command, CallbackInfo ci) {
        if (CommandDispatchEvent.MULTIPLAYER_DISPATCH.invoker().dispatch(CursedCommandSource.multiplayer(command.source), command.commandString)) {
            ci.cancel();
        }
    }
}
