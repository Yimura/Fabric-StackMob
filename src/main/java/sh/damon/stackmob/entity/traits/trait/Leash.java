package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

@TraitMetadata(assignable = MobEntity.class, path = "leash")
public class Leash implements Trait {
    @Override
    public void applyTrait(LivingEntity spawned, LivingEntity dead) {
        if (((MobEntity) dead).isLeashed())
            return;
        ((MobEntity) spawned).attachLeash(((MobEntity) dead).getHoldingEntity(), true);
    }

    @Override
    public boolean checkTrait(LivingEntity first, LivingEntity second) {
        MobEntity firstMob = (MobEntity) first;
        MobEntity secondMob = (MobEntity) second;

        return firstMob.isLeashed() == secondMob.isLeashed() || (firstMob.isLeashed() && secondMob.isLeashed() && firstMob.getHoldingEntity() == secondMob.getHoldingEntity());
    }
}
