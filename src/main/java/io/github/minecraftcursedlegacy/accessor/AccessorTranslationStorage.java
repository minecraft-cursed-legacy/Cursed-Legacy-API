package io.github.minecraftcursedlegacy.accessor;

import java.util.Properties;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.resource.language.TranslationStorage;

@Mixin(TranslationStorage.class)
public interface AccessorTranslationStorage {
	@Accessor
	Properties getTranslations();
}