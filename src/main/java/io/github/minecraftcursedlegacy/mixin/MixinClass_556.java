package io.github.minecraftcursedlegacy.mixin;

import org.lwjgl.opengl.GL11;

import org.objectweb.asm.Opcodes;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.class_556;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemInstance;

import io.github.minecraftcursedlegacy.impl.client.AtlasMapper;

@Mixin(class_556.class)
abstract class MixinClass_556 {
	@Shadow
	private Minecraft field_2401;

	@Inject(method = "method_1862",
			at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/Tessellator;INSTANCE:Lnet/minecraft/client/render/Tessellator;", opcode = Opcodes.GETSTATIC)
	)
	private void fixAtlas(LivingEntity entity, ItemInstance item, CallbackInfo info) {
		String atlas = AtlasMapper.getAtlas(item.itemId, item.getDamage());
		if (atlas != null) GL11.glBindTexture(GL11.GL_TEXTURE_2D, field_2401.textureManager.getTextureId(atlas));
	}
}