package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.ParrotEntity;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

@TraitMetadata(assignable = ParrotEntity.class, path = "parrot-variant")
public class ParrotVariant implements Trait {
    @Override
    public void applyTrait(LivingEntity spawned, LivingEntity dead) {
        ((ParrotEntity) spawned).setVariant(((ParrotEntity) dead).getVariant());
    }

    @Override
    public boolean checkTrait(LivingEntity first, LivingEntity second) {
        return ((ParrotEntity) first).getVariant() == ((ParrotEntity) second).getVariant();
    }
}
