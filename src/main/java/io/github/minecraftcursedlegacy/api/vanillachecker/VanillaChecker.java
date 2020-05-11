package io.github.minecraftcursedlegacy.api.vanillachecker;

import java.util.HashMap;

import io.github.minecraftcursedlegacy.impl.vanillachecker.VanillaCheckerImpl;
import net.minecraft.entity.player.Player;

public class VanillaChecker {
    private VanillaChecker(){/*No Constructor*/}

    /**
     * Checks if a player is using a Vanilla Client
     * @param player
     * @return
     */
    public static boolean isVanilla(Player player) {
        return VanillaCheckerImpl.playermap.get(player.name);
    }
}