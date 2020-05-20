package io.github.minecraftcursedlegacy.mixin;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import net.minecraft.client.render.ItemRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.item.ItemInstance;
import net.minecraft.sortme.TextManager;

@Mixin(ItemRenderer.class)
public class MixinItemRenderer {
    Texture texture;

    @Shadow
    public void method_1483(int i, int j, int k, int i1, int j1, int k1){
    }

    @Inject(at = @At("HEAD"), method = "method_1487", cancellable = true)
    private void method1487(TextManager arg, TextureManager arg1, ItemInstance arg2, int i, int j, CallbackInfo bruh) throws IOException {
        
        if (arg2 != null) {
            GL11.glDisable(GL11.GL_LIGHTING); //The 2896 You See All Over The Decompiled Code
            //if (texture == null)
                texture = TextureLoader.getTexture("GIF", ResourceLoader.getResourceAsStream("assets/tbgn3p08.gif"));
            System.out.println("Texture loaded: "+texture);
            System.out.println(">> Image width: "+texture.getImageWidth());
            System.out.println(">> Image height: "+texture.getImageHeight());
            System.out.println(">> Texture width: "+texture.getTextureWidth());
            System.out.println(">> Texture height: "+texture.getTextureHeight());
            System.out.println(">> Texture ID: "+texture.getTextureID());
            arg1.bindTexture(texture.getTextureID());
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
            //method_1483(i, j, arg2.method_725() % 16 * 16, arg2.method_725() / 16 * 16, 16, 16); //Don't ask
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
}