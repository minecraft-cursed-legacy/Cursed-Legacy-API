package io.github.minecraftcursedlegacy.test;

import io.github.minecraftcursedlegacy.api.registry.Id;
import io.github.minecraftcursedlegacy.api.registry.Registries;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.ItemType;

public class RegistryTest implements ModInitializer {
	@Override
	public void onInitialize() {
		System.out.println("Hello, Fabric World!");
		item = Registries.ITEM_TYPE.register(new Id("modid:item"), BasicItem::new);
	}

	public static ItemType item;
}
