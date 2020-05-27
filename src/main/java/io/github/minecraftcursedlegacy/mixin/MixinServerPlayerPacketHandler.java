package io.github.minecraftcursedlegacy.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.player.ServerPlayer;
import net.minecraft.server.network.ServerPlayerPacketHandler;

import io.github.minecraftcursedlegacy.impl.server.VanillaCheckerImpl;

@Mixin(ServerPlayerPacketHandler.class)
public class MixinServerPlayerPacketHandler {
	@Shadow
	private ServerPlayer field_920;

	@Inject(at= @At("HEAD"), method = "method_1473")
	public void method_1473(String string, Object[] objects, CallbackInfo bruh) {
		VanillaCheckerImpl.playermap.remove(field_920.name);
	}
}