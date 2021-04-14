package io.github.minecraftcursedlegacy.accessor.registry;

import net.minecraft.tile.entity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.item.TileItem;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TileEntity.class)
public interface AccessorTileEntity {
	@Invoker("register")
	static void register(Class<? extends TileEntity> clazz, String id) {}
}
