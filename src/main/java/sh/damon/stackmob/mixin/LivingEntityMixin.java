package sh.damon.stackmob.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sh.damon.stackmob.StackMob;
import sh.damon.stackmob.entity.stackentity.StackEntity;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(at = @At("HEAD"), method = "onDeath")
    public void onDeath(DamageSource source, CallbackInfo info) {
        LivingEntity entity = (LivingEntity) (Object) this;

        StackMob sm = StackMob.getInstance();
        if (!sm.entityManager.isStackedEntity(entity)) return;

        StackEntity stackEntity = sm.entityManager.getStackedEntity(entity);
        int size = stackEntity.getSize();

        sm.entityManager.unregisterStackedEntity(stackEntity);

        if (size == 1) return;

        Entity ent = entity.getType().create(entity.world);
        if (ent == null) return;

        stackEntity = sm.entityManager.registerStackedEntity((LivingEntity) ent);
        stackEntity.setSize(size - 1);

        ent.setPosition(entity.getPos());
        entity.world.spawnEntity(ent);
    }
}
