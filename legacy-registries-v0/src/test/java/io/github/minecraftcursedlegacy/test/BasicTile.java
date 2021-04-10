package io.github.minecraftcursedlegacy.test;

import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;

public class BasicTile extends Tile {
	protected BasicTile(int i, boolean fullOpaque) {
		super(i, 69, Material.DIRT);
		this.fop = fullOpaque;
	}

	private final boolean fop;

	public boolean isFullOpaque() {
		return this.fop;
	}
}
