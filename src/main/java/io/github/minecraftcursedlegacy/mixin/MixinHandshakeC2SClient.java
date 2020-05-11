package io.github.minecraftcursedlegacy.mixin;

import java.io.DataOutputStream;
import java.io.IOException;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.packet.AbstractPacket;
import net.minecraft.packet.handshake.HandshakeC2S;

@Mixin(HandshakeC2S.class)
public class MixinHandshakeC2SClient {
	@Shadow
	public int protocolVersion;
	@Shadow
	public String field_1210;
	@Shadow
	public long field_1211;
	@Shadow
	public byte field_1212;

	@Inject(at = @At("HEAD"), method = "write", cancellable = true)
	public void write(DataOutputStream dataOutputStream, CallbackInfo bruh) throws IOException {
		dataOutputStream.writeInt(this.protocolVersion);
		AbstractPacket.writeString(this.field_1210, dataOutputStream);
		dataOutputStream.writeLong(-9223372036854775808l);
		dataOutputStream.writeByte(this.field_1212);
		bruh.cancel();
	}
}