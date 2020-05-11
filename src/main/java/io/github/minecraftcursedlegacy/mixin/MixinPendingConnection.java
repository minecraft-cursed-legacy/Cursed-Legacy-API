package io.github.minecraftcursedlegacy.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.minecraftcursedlegacy.impl.vanillachecker.VanillaCheckerImpl;
import net.minecraft.packet.handshake.HandshakeC2S;
import net.minecraft.server.network.PendingConnection;

@Mixin(PendingConnection.class)
public class MixinPendingConnection {
    @Inject(at = @At("HEAD"), method = "handleHandshake")
    public void handleHandshake(HandshakeC2S arg, CallbackInfo bruh) {
        if (arg.field_1211 == -9223372036854775808l) {
            System.out.println("Fabric Client Connecting");
            VanillaCheckerImpl.playermap.put(arg.field_1210, false);
        } else {
            System.out.println("Vanilla Client Connecting");
            VanillaCheckerImpl.playermap.put(arg.field_1210, true);
        }
    }
}