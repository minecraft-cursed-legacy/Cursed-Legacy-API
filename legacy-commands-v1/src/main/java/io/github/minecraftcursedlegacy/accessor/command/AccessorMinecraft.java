package io.github.minecraftcursedlegacy.accessor.command;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Minecraft.class)
public interface AccessorMinecraft {
    @Accessor
    public static Minecraft getInstance() {
        throw new UnsupportedOperationException("mixin");
    }
}
