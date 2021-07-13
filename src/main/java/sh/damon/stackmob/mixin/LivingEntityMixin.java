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
        LivingEntity died = (LivingEntity) (Object) this;

        StackMob sm = StackMob.getInstance();
        if (!sm.entityManager.isStackedEntity(died)) return;

        StackEntity stackEntity = sm.entityManager.getStackedEntity(died);
        int size = stackEntity.getSize();

        sm.entityManager.unregisterStackedEntity(stackEntity);

        if (size == 1) return;

        LivingEntity spawned = stackEntity.duplicate();

        stackEntity = sm.entityManager.registerStackedEntity(spawned);
        stackEntity.setSize(size - 1);

        sm.traitManager.applyTraits(spawned, died);

        spawned.setPosition(died.getPos());
        died.world.spawnEntity(spawned);
    }
}
