package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

@TraitMetadata(assignable = ZombieEntity.class, path = "zombie-baby")
public class ZombieBaby implements Trait<ZombieEntity> {
    @Override
    public void applyTrait(ZombieEntity spawned, ZombieEntity dead) {
        spawned.setBaby(dead.isBaby());
    }

    @Override
    public boolean checkTrait(ZombieEntity first, ZombieEntity second) {
        return first.isBaby() == second.isBaby();
    }
}