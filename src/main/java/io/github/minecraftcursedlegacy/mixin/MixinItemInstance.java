package io.github.minecraftcursedlegacy.mixin;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.minecraftcursedlegacy.api.data.AttachedData;
import io.github.minecraftcursedlegacy.api.data.DataManager;
import io.github.minecraftcursedlegacy.api.registry.Id;
import io.github.minecraftcursedlegacy.impl.data.DataStorage;
import net.minecraft.item.ItemInstance;
import net.minecraft.util.io.CompoundTag;

@Mixin(ItemInstance.class)
public abstract class MixinItemInstance implements DataStorage {
	private CompoundTag api_data = new CompoundTag();
	private Map<Id, AttachedData> api_attachedDataMap = new HashMap<>();

	@Inject(at = @At("RETURN"), method = "copy")
	private void api_copyData(CallbackInfoReturnable<ItemInstance> info) {
		DataStorage ds = ((DataStorage) (Object) info.getReturnValue());

		this.api_attachedDataMap.forEach((id, data) -> {
			ds.putAttachedData(id, data.copy());
		});
	}

	@Inject(at = @At("RETURN"), method = "toTag")
	private void api_addData(CompoundTag tag, CallbackInfoReturnable<CompoundTag> info) {
		tag.put("moddedData", this.getModdedTag());
	}

	@Inject(at = @At("RETURN"), method = "split")
	private void api_copySplitData(int countToTake, CallbackInfoReturnable<ItemInstance> info) {
		DataStorage ds = ((DataStorage) (Object) info.getReturnValue());

		this.api_attachedDataMap.forEach((id, data) -> {
			ds.putAttachedData(id, this.api_attachedDataMap.get(id).copy());
		});
	}

	@Inject(at = @At("RETURN"), method = "fromTag")
	private void api_readData(CompoundTag tag, CallbackInfo info) {
		if (tag.containsKey("moddedData")) {
			this.api_data = tag.getCompoundTag("moddedData");
		}

		// Load Data
		DataManager.ITEM_INSTANCE.getDataKeys().forEach(id -> {
			if (this.api_data.containsKey(id.toString())) {
				this.putAttachedData(id, DataManager.ITEM_INSTANCE.deserialize((ItemInstance) (Object) this, id, this.api_data.getCompoundTag(id.toString())));
			}
		});
	}

	@Override
	public CompoundTag getModdedTag() {
		this.api_attachedDataMap.forEach((id, data) -> {
			this.api_data.put(id.toString(), data.toTag(new CompoundTag()));
		});

		return this.api_data;
	}

	@Override
	public Set<Entry<Id,AttachedData>> getAllAttachedData() {
		return this.api_attachedDataMap.entrySet();
	}

	@Override
	public void putAttachedData(Id id, AttachedData data) {
		this.api_attachedDataMap.put(id, data);
	}

	@Override
	public AttachedData getAttachedData(Id id, Supplier<AttachedData> supplier) {
		return this.api_attachedDataMap.computeIfAbsent(id, i -> supplier.get());
	}
}
