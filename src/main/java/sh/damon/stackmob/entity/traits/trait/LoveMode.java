package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.passive.AnimalEntity;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

@TraitMetadata(assignable = AnimalEntity.class, path = "love-mode")
public class LoveMode implements Trait<AnimalEntity> {
    @Override
    public void applyTrait(AnimalEntity spawned, AnimalEntity dead) {
        spawned.setLoveTicks(dead.getLoveTicks());
    }

    @Override
    public boolean checkTrait(AnimalEntity first, AnimalEntity second) {
        return first.isInLove() == second.isInLove();
    }
}
