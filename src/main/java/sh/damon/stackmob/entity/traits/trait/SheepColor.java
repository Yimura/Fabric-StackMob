package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

@TraitMetadata(assignable = SheepEntity.class, path = "sheep-color")
public class SheepColor implements Trait {
    @Override
    public void applyTrait(LivingEntity spawned, LivingEntity dead) {
        ((SheepEntity) spawned).setColor(((SheepEntity) dead).getColor());
    }

    @Override
    public boolean checkTrait(LivingEntity first, LivingEntity second) {
        return ((SheepEntity) first).getColor() != ((SheepEntity) second).getColor();
    }
}
