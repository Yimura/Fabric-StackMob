package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZoglinEntity;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

@TraitMetadata(assignable = ZoglinEntity.class, path = "zoglin-baby")
public class ZoglinBaby implements Trait<ZoglinEntity> {
    @Override
    public void applyTrait(ZoglinEntity spawned, ZoglinEntity dead) {
        spawned.setBaby(dead.isBaby());
    }

    @Override
    public boolean checkTrait(ZoglinEntity first, ZoglinEntity second) {
        return first.isBaby() == second.isBaby();
    }
}
