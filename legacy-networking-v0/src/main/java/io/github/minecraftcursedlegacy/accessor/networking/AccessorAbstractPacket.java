package io.github.minecraftcursedlegacy.accessor.networking;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.packet.AbstractPacket;

@Mixin(AbstractPacket.class)
public interface AccessorAbstractPacket {
	@Invoker("register")
	static void register(int var0, boolean flag, boolean flag1, Class arg) {
	}
}