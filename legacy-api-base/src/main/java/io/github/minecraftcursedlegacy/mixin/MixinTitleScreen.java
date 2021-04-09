package io.github.minecraftcursedlegacy.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.client.gui.screen.TitleScreen;

@Mixin(TitleScreen.class)
public class MixinTitleScreen {
	@ModifyConstant(method = "render", constant = @Constant(stringValue="Minecraft Beta 1.7.3"))
	private String render(String original) {
		return "Minecraft Beta 1.7.3 / Fabric";
	}
}
