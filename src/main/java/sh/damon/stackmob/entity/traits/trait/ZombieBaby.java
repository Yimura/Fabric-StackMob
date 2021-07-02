package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieEntity;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

@TraitMetadata(assignable = ZombieBaby.class, path = "zombie-baby")
public class ZombieBaby implements Trait {
    @Override
    public void applyTrait(LivingEntity spawned, LivingEntity dead) {
        ((ZombieEntity) spawned).setBaby(((ZombieEntity) dead).isBaby());
    }

    @Override
    public boolean checkTrait(LivingEntity first, LivingEntity second) {
        return ((ZombieEntity) first).isBaby() != ((ZombieEntity) second).isBaby();
    }
}