package io.github.minecraftcursedlegacy.test;

import net.minecraft.tile.Tile;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.entity.TileEntity;
import net.minecraft.tile.material.Material;

public class BasicTileWithEntity extends TileWithEntity {
	protected BasicTileWithEntity(int i) {
		super(i, 69, Material.DIRT);
	}

	@Override
	protected TileEntity createTileEntity() {
		return new BasicTileEntity();
	}

	static class BasicTileEntity extends TileEntity {
		@Override
		public void tick() {
			System.out.println("test tile tick!");
		}
	}
}
