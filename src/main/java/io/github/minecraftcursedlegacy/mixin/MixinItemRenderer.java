package io.github.minecraftcursedlegacy.mixin;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.minecraftcursedlegacy.impl.client.TextureRegistryImpl;

import org.newdawn.slick.opengl.Texture;
import org.objectweb.asm.Opcodes;

import net.minecraft.client.render.ItemRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemInstance;
import net.minecraft.sortme.TextManager;

@Mixin(ItemRenderer.class)
public class MixinItemRenderer {
    @Inject(at = @At("HEAD"), method = "method_1487", cancellable = true)
    private void method1487(TextManager arg, TextureManager arg1, ItemInstance arg2, int i, int j, CallbackInfo bruh) throws IOException {
        if ((arg2 != null) && (TextureRegistryImpl.getTexture(arg2.itemId) != null)) {
            GL11.glDisable(GL11.GL_LIGHTING); //The 2896 You See All Over The Decompiled Code
            Texture texture = TextureRegistryImpl.getTexture(arg2.itemId);
            arg1.bindTexture(texture.getTextureID());
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
            Tessellator var3 = Tessellator.INSTANCE;
            var3.start();
            Double z = 0D;
		    var3.vertex(i, j+16, z, 0.0D, 1.0D);
		    var3.vertex(i+16, j+16, z, 1.0D, 1.0D);
		    var3.vertex(i+16, j, z, 1.0D, 0.0D);
		    var3.vertex(i, j, z, 0.0D, 0.0D);
		    var3.draw();
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(2884);
            bruh.cancel();
        }
    }
    @Inject(at = @At(value="FIELD", target="Lnet/minecraft/client/render/Tessellator;INSTANCE:Lnet/minecraft/client/render/Tessellator;", opcode=Opcodes.GETSTATIC), method = "method_1484", cancellable = true)
    public void method_1484_0(ItemEntity arg, double d, double d1, double d2, float f, float f1, CallbackInfo bruh) {
        if ((arg.item != null) && (TextureRegistryImpl.getTexture(arg.item.itemId) != null)) {
            GL11.glBindTexture(3553, TextureRegistryImpl.getTexture(arg.item.itemId).getTextureID());
            TextureRegistryImpl.vertexHacksEnabled = true;
            TextureRegistryImpl.var4hack = arg.item.method_725();
            System.out.println("e");
        }
    }
    @Inject(at = @At("TAIL"), method = "method_1484")
    public void method_1484_1(ItemEntity arg, double d, double d1, double d2, float f, float f1, CallbackInfo bruh) {
        TextureRegistryImpl.vertexHacksEnabled = false;
    }

}