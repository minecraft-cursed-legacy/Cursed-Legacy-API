package io.github.minecraftcursedlegacy.accessor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;

@Mixin(EntityRegistry.class)
public interface AccessorEntityRegistry {
	@Accessor("ID_TO_CLASS")
	static Map<Integer, Class<? extends Entity>> getID_TO_CLASS() {
		throw new AssertionError("mixin");
	}

	@Accessor("ID_TO_CLASS")
	static void setID_TO_CLASS(Map<Integer, Class<? extends Entity>> value) {
		throw new AssertionError("mixin");
	}

	@Accessor("CLASS_TO_ID")
	static void setCLASS_TO_ID(Map<Class<? extends Entity>, Integer> value) {
		throw new AssertionError("mixin");
	}

	@Accessor("CLASS_TO_ID")
	static Map<Class<? extends Entity>, Integer> getCLASS_TO_ID() {
		throw new AssertionError("mixin");
	}

	@Accessor("CLASS_TO_STRING_ID")
	static Map<Class<? extends Entity>, String> getCLASS_TO_STRING_ID() {
		throw new AssertionError("mixin");
	}

	@Accessor("CLASS_TO_STRING_ID")
	static void setCLASS_TO_STRING_ID(Map<Class<? extends Entity>, String> value) {
		throw new AssertionError("mixin");
	}

	@Accessor("STRING_ID_TO_CLASS")
	static Map<String, Class<? extends Entity>> getSTRING_ID_TO_CLASS() {
		throw new AssertionError("mixin");
	}

	@Accessor("STRING_ID_TO_CLASS")
	static void setSTRING_ID_TO_CLASS(Map<String, Class<? extends Entity>> value) {
		throw new AssertionError("mixin");
	}

	@Invoker
	static void callRegister(Class<? extends Entity> arg, String string, int i) {
		throw new AssertionError("mixin");
	}
}
