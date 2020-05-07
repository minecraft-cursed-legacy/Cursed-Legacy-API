package io.github.minecraftcursedlegacy.test;

import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;

public class BasicTile extends Tile {
	protected BasicTile(int i) {
		super(i, 69, Material.DIRT);
	}
}
