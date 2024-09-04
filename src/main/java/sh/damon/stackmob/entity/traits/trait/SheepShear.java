package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

@TraitMetadata(assignable = SheepEntity.class, path = "sheep-sheared")
public class SheepShear implements Trait<SheepEntity> {
    @Override
    public void applyTrait(SheepEntity spawned, SheepEntity dead) {
        spawned.setSheared(dead.isSheared());
    }

    @Override
    public boolean checkTrait(SheepEntity first, SheepEntity second) {
        return first.isSheared() == second.isSheared();
    }
}
