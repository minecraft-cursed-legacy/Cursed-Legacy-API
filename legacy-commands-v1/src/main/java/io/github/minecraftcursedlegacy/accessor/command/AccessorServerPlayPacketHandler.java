package io.github.minecraftcursedlegacy.accessor.command;

import net.minecraft.server.network.ServerPlayPacketHandler;
import net.minecraft.server.player.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerPlayPacketHandler.class)
public interface AccessorServerPlayPacketHandler {
    @Accessor
    public abstract ServerPlayer getPlayer();
}
