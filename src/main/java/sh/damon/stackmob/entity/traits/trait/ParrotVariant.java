package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.ParrotEntity;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

@TraitMetadata(assignable = ParrotEntity.class, path = "parrot-variant")
public class ParrotVariant implements Trait<ParrotEntity> {
    @Override
    public void applyTrait(ParrotEntity spawned, ParrotEntity dead) {
        spawned.setVariant(dead.getVariant());
    }

    @Override
    public boolean checkTrait(ParrotEntity first, ParrotEntity second) {
        return first.getVariant() == second.getVariant();
    }
}
