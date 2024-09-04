package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinEntity;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

@TraitMetadata(assignable = PiglinEntity.class, path = "piglin-baby")
public class PiglinBaby implements Trait<PiglinEntity> {
    @Override
    public void applyTrait(PiglinEntity spawned, PiglinEntity dead) {
        spawned.setBaby(dead.isBaby());
    }

    @Override
    public boolean checkTrait(PiglinEntity first, PiglinEntity second) {
        return first.isBaby() == second.isBaby();
    }
}
