package io.github.minecraftcursedlegacy.mixin;

import org.lwjgl.opengl.GL11;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.minecraftcursedlegacy.impl.client.TextureRegistryImpl;
import net.minecraft.class_556;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemInstance;

@Mixin(class_556.class)
public class MixinClass_556 {
    @Inject(at = @At("TAIL"), method = "method_1862")
    private void method_1862_0(LivingEntity arg, ItemInstance arg1, CallbackInfo bruh) {
        TextureRegistryImpl.vertexHacksEnabled = false;
    }

    @Inject(at = @At(value="FIELD", target="Lnet/minecraft/client/render/Tessellator;INSTANCE:Lnet/minecraft/client/render/Tessellator;", opcode=Opcodes.GETSTATIC), method = "method_1862", cancellable = true)
    private void method_1862_1(LivingEntity arg, ItemInstance arg1, CallbackInfo bruh) {
        if ((arg1 != null) && (TextureRegistryImpl.getTexture(arg1.itemId) != null)) {
            GL11.glBindTexture(3553, TextureRegistryImpl.getTexture(arg1.itemId).getTextureID());
            TextureRegistryImpl.vertexHacksEnabled = true;
            TextureRegistryImpl.var4hack = arg.method_917(arg1);
        }
    }
}