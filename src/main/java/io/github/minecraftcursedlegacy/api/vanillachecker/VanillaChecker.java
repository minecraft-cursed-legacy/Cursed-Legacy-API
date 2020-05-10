package io.github.minecraftcursedlegacy.api.vanillachecker;

import java.util.HashMap;

import net.minecraft.entity.player.Player;

public class VanillaChecker {
    private VanillaChecker(){/*No Constructor*/}

    /**
     * Don't Touch!
     */
    public static HashMap<String, Boolean> playermap = new HashMap<String, Boolean>();

    public static boolean isVanilla(Player player) {
        return playermap.get(player.name);
    }
}