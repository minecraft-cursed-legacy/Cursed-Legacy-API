package io.github.minecraftcursedlegacy.api.command;

import io.github.minecraftcursedlegacy.impl.command.SingleplayerCursedCommandSourceImpl;
import net.minecraft.entity.player.Player;

import javax.annotation.Nullable;

public interface CursedCommandSource {
    @Nullable Player getPlayer();
    String getName();
    void sendCommandFeedback(String message);
    void sendError(String message);

    static CursedCommandSource singleplayer(Player player) {
        return new SingleplayerCursedCommandSourceImpl(player);
    }

    static CursedCommandSource multiplayer(Player player) {
        return new SingleplayerCursedCommandSourceImpl(player);
    }
}
