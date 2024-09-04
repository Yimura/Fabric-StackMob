package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.CatEntity;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

@TraitMetadata(assignable = CatEntity.class, path = "cat-type")
public class CatType  implements Trait<CatEntity> {
    @Override
    public void applyTrait(CatEntity spawned, CatEntity dead) {
        spawned.setVariant(dead.getVariant());
    }

    @Override
    public boolean checkTrait(CatEntity first, CatEntity second) {
        return first.getVariant() == second.getVariant();
    }
}
