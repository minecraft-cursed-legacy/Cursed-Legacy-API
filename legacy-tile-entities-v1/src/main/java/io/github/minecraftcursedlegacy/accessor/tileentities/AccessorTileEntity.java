package io.github.minecraftcursedlegacy.accessor.tileentities;

import net.minecraft.tile.entity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;

@Mixin(TileEntity.class)
public interface AccessorTileEntity {
	@Invoker("register")
	static void register(Class<? extends TileEntity> clazz, String id) {}

	@Accessor("ID_TO_CLASS")
	static Map<String, Class<? extends TileEntity>> getIdToClassMap() { throw new UnsupportedOperationException("mixin"); }

	@Accessor("CLASS_TO_ID")
	static Map<Class<? extends TileEntity>, String> getClassToIdMap() { throw new UnsupportedOperationException("mixin"); }
}
