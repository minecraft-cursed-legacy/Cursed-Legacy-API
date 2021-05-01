package io.github.minecraftcursedlegacy.impl.command;

import io.github.minecraftcursedlegacy.accessor.command.AccessorServerPlayPacketHandler;
import io.github.minecraftcursedlegacy.api.command.CursedCommandSource;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.Player;
import net.minecraft.server.command.CommandSource;

@Environment(EnvType.SERVER)
public class MultiplayerCursedCommandSourceImpl implements CursedCommandSource {
    CommandSource source = null;

    public MultiplayerCursedCommandSourceImpl(CommandSource source) {
        this.source = source;
    }

    public Player getPlayer() {
        if (source instanceof AccessorServerPlayPacketHandler) {
            return ((AccessorServerPlayPacketHandler) source).getPlayer();
        }
        return null;
    }

    public String getName() {
        return source.getName();
    }

    public void sendCommandFeedback(String message) {
        source.sendFeedback(message);
    }

    public void sendError(String message) {
        source.sendFeedback("§4"+message);
    }
}
