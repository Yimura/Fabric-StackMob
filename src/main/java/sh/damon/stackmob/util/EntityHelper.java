package sh.damon.stackmob.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class EntityHelper {
    public static LivingEntity createNewEntity(ServerWorld world, Identifier entityId, Vec3d position) {
        return createNewEntity(world, entityId, position, new NbtCompound());
    }

    public static LivingEntity createNewEntity(ServerWorld world, Identifier entityId, Vec3d position, NbtCompound nbt) {
        nbt.putString("id", entityId.toString());

        Entity entity = EntityType.loadEntityWithPassengers(nbt, world, ent -> {
            ent.refreshPositionAndAngles(position, ent.getYaw(), ent.getPitch());
            return ent;
        });
        if (entity == null) {
            return null;
        }
        if (entity instanceof MobEntity) {
            ((MobEntity) entity).initialize(world, world.getLocalDifficulty(entity.getBlockPos()), SpawnReason.COMMAND, null);
        }

        return (LivingEntity) entity;
    }

    public static LivingEntity createNewEntity(LivingEntity other) {
        return createNewEntity(other, other.getPos());
    }

    public static LivingEntity createNewEntity(LivingEntity other, Vec3d position) {
        return createNewEntity((ServerWorld) other.getWorld(), EntityType.getId(other.getType()), position);
    }

    public static boolean spawnEntity(LivingEntity livingEntity) {
        return ((ServerWorld) livingEntity.getWorld()).spawnNewEntityAndPassengers(livingEntity);
    }
}
