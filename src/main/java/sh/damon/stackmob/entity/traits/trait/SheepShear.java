package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

@TraitMetadata(assignable = SheepEntity.class, path = "sheep-sheared")
public class SheepShear implements Trait {
    @Override
    public void applyTrait(LivingEntity spawned, LivingEntity dead) {
        ((SheepEntity) spawned).setSheared(((SheepEntity) dead).isSheared());
    }

    @Override
    public boolean checkTrait(LivingEntity first, LivingEntity second) {
        return ((SheepEntity) first).isSheared() != ((SheepEntity) second).isSheared();
    }
}
