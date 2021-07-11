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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sh.damon.stackmob.StackMob;
import sh.damon.stackmob.entity.stackentity.StackEntity;

import java.util.stream.Stream;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
    @Inject(at = @At("RETURN"), method = "spawnEntity")
    public void spawnEntity(Entity entity, CallbackInfoReturnable<Boolean> info) {
        if (entity instanceof MobEntity) {
            StackMob sm = StackMob.getInstance();
            if (sm.entityManager.isStackedEntity((LivingEntity) entity)) return;

            BlockPos block = entity.getBlockPos();
            Box box = new Box(
                block.add(-5, -5, -5),
                block.add(5,5,5)
            );

            StackEntity original = sm.entityManager.registerStackedEntity((LivingEntity) entity);
            if (!original.canStack()) return;

            for (Entity ent : entity.world.getOtherEntities(entity, box)) {
                if (!(ent instanceof MobEntity)) continue;

                StackEntity other = sm.entityManager.getStackedEntity((LivingEntity) ent);
                if (other == null || !other.canStack()) continue;
                if (sm.traitManager.checkTraits(original, other)) continue;

                sm.entityManager.unregisterStackedEntity(
                    other.merge(original)
                ).remove(Entity.RemovalReason.DISCARDED);
            }
        }
    }
}
