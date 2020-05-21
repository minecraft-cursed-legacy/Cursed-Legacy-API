package io.github.minecraftcursedlegacy.mixin;

import org.objectweb.asm.Opcodes;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.ItemRenderer;
import net.minecraft.client.render.Renderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.entity.ItemEntity;
import net.minecraft.sortme.TextManager;

import io.github.minecraftcursedlegacy.impl.client.AtlasMapper;

@Mixin(ItemRenderer.class)
abstract class MixinItemRenderer extends Renderer {
	@Inject(method = "method_1484",
			at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/Tessellator;INSTANCE:Lnet/minecraft/client/render/Tessellator;", opcode = Opcodes.GETSTATIC)
	) //This is an overload of render
	private void fixAtlas(ItemEntity entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo info) {
		AtlasMapper.getAtlas(dispatcher.textureManager, entity.item.itemId, entity.item.getDamage()).ifPresent(dispatcher.textureManager::bindTexture);
	}

	@Inject(method = "method_1486",
			at = @At(value = "FIELD", target = "Lnet/minecraft/item/ItemType;byId:[Lnet/minecraft/item/ItemType;", opcode = Opcodes.GETSTATIC)
	)
	private void fixAtlas(TextManager textManager, TextureManager textureManager, int itemID, int meta, int texturePosition, int x, int y, CallbackInfo info) {
		AtlasMapper.getAtlas(textureManager, itemID, meta).ifPresent(textureManager::bindTexture);
	}
}