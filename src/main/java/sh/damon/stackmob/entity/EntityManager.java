package sh.damon.stackmob.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
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

    public boolean isRegistered(LivingEntity ent) {
        return this.entities.containsKey(ent.getUuid());
    }

    public StackEntity register(LivingEntity entity) {
        StackEntity stackEntity = new StackEntity(entity);
        entities.put(entity.getUuid(), stackEntity);

        StackMob.log.info("Registered new StackEntity: " + entity.getUuidAsString());

        return stackEntity;
    }

    public LivingEntity unregisterStackedEntity(StackEntity stackEntity) {
        if (stackEntity == null) return null;

        stackEntity.setRemoved();

        LivingEntity entity = stackEntity.getEntity();

        this.entities.remove(entity.getUuid());

        StackMob.log.info("Removed StackEntity: " + entity.getUuidAsString());

        return entity;
    }
}
