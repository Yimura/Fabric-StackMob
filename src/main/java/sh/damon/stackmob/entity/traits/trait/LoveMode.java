package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

@TraitMetadata(assignable = AnimalEntity.class, path = "love-mode")
public class LoveMode implements Trait {
    @Override
    public void applyTrait(LivingEntity spawned, LivingEntity dead) {
        ((AnimalEntity) spawned).setLoveTicks(((AnimalEntity) dead).getLoveTicks());
    }

    @Override
    public boolean checkTrait(LivingEntity first, LivingEntity second) {
        return ((AnimalEntity) first).getLoveTicks() != 0 || ((AnimalEntity) second).getLoveTicks() != 0;
    }
}
