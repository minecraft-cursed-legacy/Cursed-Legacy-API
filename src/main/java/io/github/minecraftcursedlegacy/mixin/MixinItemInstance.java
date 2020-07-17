package io.github.minecraftcursedlegacy.mixin;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.minecraftcursedlegacy.api.data.ModdedData;
import io.github.minecraftcursedlegacy.api.data.ModdedDataManager;
import io.github.minecraftcursedlegacy.api.registry.Id;
import io.github.minecraftcursedlegacy.impl.data.DataStorage;
import net.minecraft.item.ItemInstance;
import net.minecraft.util.io.CompoundTag;

@Mixin(ItemInstance.class)
public abstract class MixinItemInstance implements DataStorage {
	private CompoundTag api_data = new CompoundTag();
	private Map<Id, ModdedData> api_moddedDataMap = new HashMap<>();

	@Inject(at = @At("RETURN"), method = "copy")
	private void api_copyData(CallbackInfoReturnable<ItemInstance> info) {
		DataStorage ds = ((DataStorage) (Object) info.getReturnValue());

		this.api_moddedDataMap.forEach((id, data) -> {
			ds.putModdedData(id, this.api_moddedDataMap.get(id).copy());
		});
	}

	@Inject(at = @At("RETURN"), method = "toTag")
	private void api_addData(CompoundTag tag, CallbackInfoReturnable<CompoundTag> info) {
		tag.put("moddedData", this.getModdedTag());
	}

	@Inject(at = @At("RETURN"), method = "split")
	private void split(int countToTake, CallbackInfoReturnable<ItemInstance> info) {
		DataStorage ds = ((DataStorage) (Object) info.getReturnValue());

		this.api_moddedDataMap.forEach((id, data) -> {
			ds.putModdedData(id, this.api_moddedDataMap.get(id).copy());
		});
	}

	@Inject(at = @At("RETURN"), method = "fromTag")
	private void api_readData(CompoundTag tag, CallbackInfo info) {
		if (tag.containsKey("moddedData")) {
			this.api_data = tag.getCompoundTag("moddedData");
		}

		// Load Data
		ModdedDataManager.ITEM_INSTANCE.getDataKeys().forEach(id -> {
			if (this.api_data.containsKey(id.toString())) {
				this.putModdedData(id, ModdedDataManager.ITEM_INSTANCE.deserialize((ItemInstance) (Object) this, id, this.api_data.getCompoundTag(id.toString())));
			}
		});
	}

	@Override
	public CompoundTag getModdedTag() {
		this.api_moddedDataMap.forEach((id, data) -> {
			this.api_data.put(id.toString(), data.toTag(new CompoundTag()));
		});

		return this.api_data;
	}

	@Override
	public void putModdedData(Id id, ModdedData data) {
		this.api_moddedDataMap.put(id, data);
	}

	@Override
	public ModdedData getModdedData(Id id, Supplier<ModdedData> supplier) {
		return this.api_moddedDataMap.computeIfAbsent(id, i -> supplier.get());
	}
}
