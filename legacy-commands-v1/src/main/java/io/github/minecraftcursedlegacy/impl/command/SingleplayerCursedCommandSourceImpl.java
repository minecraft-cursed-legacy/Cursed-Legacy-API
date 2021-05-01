package io.github.minecraftcursedlegacy.impl.command;

import io.github.minecraftcursedlegacy.api.command.CursedCommandSource;
import net.minecraft.entity.player.Player;

public class SingleplayerCursedCommandSourceImpl implements CursedCommandSource {
    Player player = null;

    public SingleplayerCursedCommandSourceImpl(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public String getName() {
        return player.name;
    }

    public void sendCommandFeedback(String message) {
        player.sendTranslatedMessage("menu.quit");
    }

    public void sendError(String message) {

    }
}
