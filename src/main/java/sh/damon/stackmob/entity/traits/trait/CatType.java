package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.CatEntity;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

@TraitMetadata(assignable = CatEntity.class, path = "cat-type")
public class CatType  implements Trait {
    @Override
    public void applyTrait(LivingEntity spawned, LivingEntity dead) {
        ((CatEntity) spawned).setCatType(((CatEntity) dead).getCatType());
    }

    @Override
    public boolean checkTrait(LivingEntity first, LivingEntity second) {
        return ((CatEntity) first).getCatType() == ((CatEntity) second).getCatType();
    }
}
