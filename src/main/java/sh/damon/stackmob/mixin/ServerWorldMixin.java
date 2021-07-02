package sh.damon.stackmob.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sh.damon.stackmob.StackMob;
import sh.damon.stackmob.entity.stackentity.StackEntity;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
    @Inject(at = @At("HEAD"), method = "spawnEntity")
    public void spawnEntity(Entity entity, CallbackInfoReturnable<Boolean> info) {
        if (entity instanceof MobEntity) {
            BlockPos block = entity.getBlockPos();
            Box box = new Box(
                block.add(-5, -5, -5),
                block.add(5,5,5)
            );

            StackMob sm = StackMob.getInstance();
            StackEntity original = sm.entityManager.registerStackedEntity((LivingEntity) entity);

            for (Entity ent : entity.world.getOtherEntities(entity, box)) {
                if (!(ent instanceof MobEntity)) continue;

                StackEntity other = sm.entityManager.getStackedEntity((LivingEntity) ent);
                if (other == null) {
                    StackMob.log.info("SPAWNENTITY | Other entity was not a StackEntity instance, skipping...");

                    continue;
                }
                if (!original.canStack()) {
                    StackMob.log.info("SPAWNENTITY | Original is unable to stack, skipping...");

                    continue;
                }

                sm.entityManager.unregisterStackedEntity(
                    other.merge(original)
                );
            }
        }
    }
}
