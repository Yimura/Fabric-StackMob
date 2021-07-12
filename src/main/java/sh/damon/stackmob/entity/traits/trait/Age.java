package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.LivingEntity;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

@TraitMetadata(assignable = LivingEntity.class, path = "age")
public class Age implements Trait {
    @Override
    public void applyTrait(LivingEntity spawned, LivingEntity dead) {
        spawned.age = dead.age;
    }

    /**
     * Compares the age of the two entities (only if adult or not)
     * @param first the initial entity.
     * @param second the other entity the first should stack with
     * @return True if they're both adult or both baby
     */
    @Override
    public boolean checkTrait(LivingEntity first, LivingEntity second) {
        return (first.age >= 0) == (second.age >= 0);
    }
}
