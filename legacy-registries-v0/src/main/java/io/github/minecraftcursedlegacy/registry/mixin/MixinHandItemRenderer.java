package io.github.minecraftcursedlegacy.registry.mixin;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.minecraftcursedlegacy.registry.impl.client.AtlasMapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.HandItemRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemInstance;

@Deprecated
@Mixin(HandItemRenderer.class)
abstract class MixinHandItemRenderer {
	@Shadow
	private Minecraft field_2401;

	@Inject(method = "method_1862",
			at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/Tessellator;INSTANCE:Lnet/minecraft/client/render/Tessellator;", opcode = Opcodes.GETSTATIC)
			)
	private void fixAtlas(LivingEntity entity, ItemInstance item, CallbackInfo info) {
		AtlasMapper.getAtlas(field_2401.textureManager, item.itemId, item.getDamage()).ifPresent(field_2401.textureManager::bindTexture);
	}
}