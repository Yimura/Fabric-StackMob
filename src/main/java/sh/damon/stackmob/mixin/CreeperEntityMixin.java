package sh.damon.stackmob.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.CreeperEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sh.damon.stackmob.StackMob;
import sh.damon.stackmob.entity.StackEntity;

@Mixin(CreeperEntity.class)
public class CreeperEntityMixin {
    @Inject(at = @At("RETURN"), method = "explode")
    public void onExplode(CallbackInfo ci) {
        CreeperEntity died = (CreeperEntity) (Object) this;

        StackMob sm = StackMob.getInstance();
        if (!sm.entityManager.isRegistered(died)) return;

        StackEntity stackEntity = sm.entityManager.getStackedEntity(died);
        sm.entityManager.unregisterStackedEntity(stackEntity);

        int size = stackEntity.getSize();
        if (size == 1) return;

        LivingEntity spawned = stackEntity.duplicate();

        sm.traitManager.applyTraits(spawned, died);

        spawned.setPosition(died.getPos());
        died.world.spawnEntity(spawned);

        stackEntity = sm.entityManager.register(spawned);
        stackEntity.setSize(size - 1);
    }
}
