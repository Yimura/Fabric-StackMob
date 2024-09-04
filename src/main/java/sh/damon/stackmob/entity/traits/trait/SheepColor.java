package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SheepEntity;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

@TraitMetadata(assignable = SheepEntity.class, path = "sheep-color")
public class SheepColor implements Trait<SheepEntity> {
    @Override
    public void applyTrait(SheepEntity spawned, SheepEntity dead) {
        spawned.setColor(dead.getColor());
    }

    @Override
    public boolean checkTrait(SheepEntity first, SheepEntity second) {
        return first.getColor() == second.getColor();
    }
}
