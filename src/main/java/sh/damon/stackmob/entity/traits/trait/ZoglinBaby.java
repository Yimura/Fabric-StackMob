package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZoglinEntity;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

@TraitMetadata(assignable = ZoglinEntity.class, path = "zoglin-baby")
public class ZoglinBaby implements Trait {
    @Override
    public void applyTrait(LivingEntity spawned, LivingEntity dead) {
        ((ZoglinEntity) spawned).setBaby(((ZoglinEntity) dead).isBaby());
    }

    @Override
    public boolean checkTrait(LivingEntity first, LivingEntity second) {
        return ((ZoglinEntity) first).isBaby() == ((ZoglinEntity) second).isBaby();
    }
}
