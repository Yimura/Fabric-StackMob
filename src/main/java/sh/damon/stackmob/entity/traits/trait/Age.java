package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

@TraitMetadata(assignable = AnimalEntity.class, path = "age")
public class Age implements Trait<AnimalEntity> {
    @Override
    public void applyTrait(AnimalEntity spawned, AnimalEntity dead) {
        spawned.setBreedingAge(dead.getBreedingAge());
    }

    /**
     * Compares the age of the two entities (only if adult or not)
     * @param first the initial entity.
     * @param second the other entity the first should stack with
     * @return True if they're both adult or both baby
     */
    @Override
    public boolean checkTrait(AnimalEntity first, AnimalEntity second) {
        return (first.isBaby()) == (second.isBaby());
    }
}
