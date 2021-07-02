package sh.damon.stackmob.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import sh.damon.stackmob.entity.stackentity.StackEntity;

import java.util.HashMap;
import java.util.UUID;

public class EntityManager {
    private final HashMap<UUID, StackEntity> entities = new HashMap<>();

    public EntityManager() {

    }

    public StackEntity getStackedEntity(LivingEntity entity) {
        return this.entities.get(entity.getUuid());
    }

    public boolean isStackedEntity(LivingEntity ent) {
        return this.entities.containsKey(ent.getUuid());
    }

    public void registerAll(MinecraftServer server) {
        for (ServerWorld world : server.getWorlds()) {
            for (Entity entity : world.iterateEntities()) {
                if (!(entity instanceof MobEntity)) continue;

                this.registerStackedEntity((LivingEntity) entity);
            }
        }
    }

    public StackEntity registerStackedEntity(LivingEntity entity) {
        StackEntity stackEntity = new StackEntity(entity);
        entities.put(entity.getUuid(), stackEntity);

        return stackEntity;
    }

    public void unregisterStackedEntity(StackEntity stackEntity) {
        if (stackEntity == null) return;

        stackEntity.setRemoved();

        LivingEntity entity = stackEntity.getEntity();

        this.entities.remove(entity.getUuid());
        entity.remove(Entity.RemovalReason.DISCARDED);
    }
}
