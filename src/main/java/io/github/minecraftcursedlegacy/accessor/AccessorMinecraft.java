package io.github.minecraftcursedlegacy.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MinecraftApplet;

@Mixin(Minecraft.class)
public interface AccessorMinecraft {
	@Accessor("field_2765")
	MinecraftApplet getApplet();
}