package io.github.minecraftcursedlegacy.impl.registry;

import io.github.minecraftcursedlegacy.api.registry.Id;
import io.github.minecraftcursedlegacy.api.registry.Registry;
import io.github.minecraftcursedlegacy.mixin.MixinEntityRegistry;
import net.minecraft.entity.Entity;

import java.util.HashMap;
import java.util.Map;

public class EntityTypeRegistry extends Registry<EntityType> {

    private int currentId = 0;

    /**
     * Creates a new registry object.
     *
     * @param registryName the identifier for this registry.
     */
    public EntityTypeRegistry(Id registryName) {
        super(EntityType.class, registryName, null);

        // add vanilla entities
        MixinEntityRegistry.getID_TO_CLASS().forEach((intId, clazz) -> {
            if (clazz != null) {
                String idPart = MixinEntityRegistry.getCLASS_TO_STRING_ID().get(clazz);

                EntityType type = new EntityType(clazz, idPart == null ? "entity" : idPart);
                if (idPart == null) {
                    idPart = "entity";
                } else {
                    idPart = idPart.toLowerCase();
                }

                this.byRegistryId.put(new Id(idPart), type);
                this.bySerialisedId.put(intId, type);
            }
        });
    }

    @Override
    protected int getNextSerialisedId() {
        Map<Integer, Class<? extends Entity>> idToClass = MixinEntityRegistry.getID_TO_CLASS();
        while (idToClass.containsKey(currentId)) {
            ++currentId;
        }

        return currentId;
    }

    @Override
    protected int getStartSerialisedId() {
        return 1; //Maybe this could be changed to 0, not sure if vanilla would like an entity having 0 as an id.
    }

    @Override
    protected void beforeRemap() {
        MixinEntityRegistry.setID_TO_CLASS(new HashMap<>());
        MixinEntityRegistry.setCLASS_TO_ID(new HashMap<>());
        MixinEntityRegistry.setSTRING_ID_TO_CLASS(new HashMap<>());
        MixinEntityRegistry.setCLASS_TO_STRING_ID(new HashMap<>());
    }

    @Override
    protected void onRemap(EntityType remappedValue, int newSerialisedId) {
        MixinEntityRegistry.callRegister(remappedValue.getClazz(), remappedValue.getVanillaRegistryStringId(), newSerialisedId);
    }

}
