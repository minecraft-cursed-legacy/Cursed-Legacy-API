package io.github.minecraftcursedlegacy.mixin.worldtype;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.minecraftcursedlegacy.impl.worldtype.WorldTypeImpl;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.screen.CreateLevelScreen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.resource.language.TranslationStorage;

@Mixin(CreateLevelScreen.class)
public abstract class MixinCreateLevelScreen extends Screen {
	@SuppressWarnings("unchecked")
	@Inject(at = @At("RETURN"), method = "init")
	private void api_onInit(CallbackInfo info) {
		// TODO use TranslationStorage.getInstance()
		this.buttons.add(new Button(2, this.width / 2, 2 * this.height / 3 - 5, 100, 20,
				TranslationStorage.getInstance().translate(WorldTypeImpl.getSelected().toString()))); // Add button
		((Button) this.buttons.get(0)).y += 15; // Shift the create world and cancel buttons down
		((Button) this.buttons.get(1)).y += 15;
	}

	@Inject(at = @At("RETURN"), method = "render")
	private void api_onRender(int mouseX, int mouseY, float tickDelta, CallbackInfo info) {
		this.drawTextWithShadow(this.textManager, "World Type", this.width / 2 - 100, 2 * this.height / 3, 10526880); // Draw text (with shadow)
	}
	
	@Inject(at = @At("HEAD"), method = "buttonClicked", cancellable = true)
	private void api_buttonClicked(Button button, CallbackInfo info) {
		if (button.active && button.id == 2) {
			// Cycle world type
			button.text = TranslationStorage.getInstance().translate(WorldTypeImpl.cycle().toString());
		}
	}
}
