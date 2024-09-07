package sh.damon.stackmob.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import sh.damon.stackmob.StackMob;

import java.util.HashMap;
import java.util.UUID;

public class EntityManager {
    private final HashMap<UUID, StackEntity> entities = new HashMap<>();

    public EntityManager() {

    }

    public StackEntity getStackedEntity(LivingEntity entity) {
        return this.entities.get(entity.getUuid());
    }

    public StackEntity getOrRegister(LivingEntity entity) {
        return this.entities.containsKey(entity.getUuid()) ? getStackedEntity(entity) : register(entity);
    }

    public boolean isRegistered(LivingEntity ent) {
        return this.entities.containsKey(ent.getUuid());
    }

    public StackEntity register(LivingEntity entity) {
        Identifier identifier = Registries.ENTITY_TYPE.getId(entity.getType());
        var entityConfig = StackMob.getInstance().getConfig().getConfigByIdentifier(identifier);

        StackEntity stackEntity = new StackEntity(entity, entityConfig);
        entities.put(entity.getUuid(), stackEntity);

        return stackEntity;
    }

    public LivingEntity unregisterStackedEntity(StackEntity stackEntity) {
        if (stackEntity == null) return null;

        stackEntity.setRemoved();

        LivingEntity entity = stackEntity.getEntity();

        this.entities.remove(entity.getUuid());

        return entity;
    }
}
