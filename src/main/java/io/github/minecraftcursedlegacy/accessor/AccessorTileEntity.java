package io.github.minecraftcursedlegacy.accessor;

import net.minecraft.entity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;

@Mixin(TileEntity.class)
public interface AccessorTileEntity {
	@Accessor("ID_TO_CLASS")
	static Map<String, Class<? extends TileEntity>> getIdToClassMap() {
		throw new AssertionError("mixin");
	}

	@Accessor("ID_TO_CLASS")
	static void setIdToClassMap(Map<String, Class<? extends TileEntity>> value) {
		throw new AssertionError("mixin");
	}

	@Accessor("CLASS_TO_ID")
	static Map<Class<? extends TileEntity>, String> getClassToIdMap() {
		throw new AssertionError("mixin");
	}

	@Accessor("CLASS_TO_ID")
	static void setClassToIdMap(Map<Class<? extends TileEntity>, String> value) {
		throw new AssertionError("mixin");
	}

	@Invoker("register")
	static void callRegister(Class<? extends TileEntity> arg, String string) {
		throw new AssertionError("mixin");
	}
}
