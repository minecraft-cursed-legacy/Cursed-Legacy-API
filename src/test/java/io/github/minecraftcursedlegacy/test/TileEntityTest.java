package io.github.minecraftcursedlegacy.test;

import io.github.minecraftcursedlegacy.api.recipe.Recipes;
import io.github.minecraftcursedlegacy.api.registry.Id;
import io.github.minecraftcursedlegacy.api.registry.Registries;
import io.github.minecraftcursedlegacy.api.registry.TileItems;
import io.github.minecraftcursedlegacy.impl.registry.TileEntityType;
import io.github.minecraftcursedlegacy.impl.registry.TileEntityTypeRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.TileEntity;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.ItemType;
import net.minecraft.tile.Tile;
import net.minecraft.tile.TileWithEntity;
import net.minecraft.tile.material.Material;

public class TileEntityTest implements ModInitializer {
    @Override
    public void onInitialize() {
        Tile tile = Registries.TILE.register(new Id("test:tileWithEntity"),
                i -> new TestTileWithEntity(i).setName("TileWithEntity"));
        TileItems.registerTileItem(new Id("test:tileWithEntity"), tile);

        Registries.TILE_ENTITY_TYPE.register(new Id("test:tileEntity"), i -> new TileEntityType(TestTileEntity.class, new Id("test:tileEntity")));

        ItemType tileItem = TileItems.registerTileItem(new Id("modid:tile"), tile);
        Recipes.addShapelessRecipe(new ItemInstance(tileItem), Tile.DIRT, Tile.SAND);
    }

    private static class TestTileWithEntity extends TileWithEntity {
        protected TestTileWithEntity(int id) {
            super(id, Material.METAL);
        }

        @Override
        protected TileEntity createTileEntity() {
            return new TestTileEntity();
        }
    }

    private static class TestTileEntity extends TileEntity {
        @Override
        public void tick() {
            super.tick();
            System.out.println(level.getLevelTime());
        }
    }
}
