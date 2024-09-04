package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

@TraitMetadata(assignable = MobEntity.class, path = "leash")
public class Leash implements Trait<MobEntity> {
    @Override
    public void applyTrait(MobEntity spawned, MobEntity dead) {
        if (dead.isLeashed())
            return;
        spawned.attachLeash(dead.getLeashHolder(), true);
    }

    @Override
    public boolean checkTrait(MobEntity first, MobEntity second) {
        return first.isLeashed() == second.isLeashed() || (first.isLeashed() && second.isLeashed() && first.getLeashHolder() == second.getLeashHolder());
    }
}
