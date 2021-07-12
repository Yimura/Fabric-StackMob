package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinEntity;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

@TraitMetadata(assignable = PiglinEntity.class, path = "piglin-baby")
public class PiglinBaby implements Trait {
    @Override
    public void applyTrait(LivingEntity spawned, LivingEntity dead) {
        ((PiglinEntity) spawned).setBaby(((PiglinEntity) dead).isBaby());
    }

    @Override
    public boolean checkTrait(LivingEntity first, LivingEntity second) {
        return ((PiglinEntity) first).isBaby() == ((PiglinEntity) second).isBaby();
    }
}
