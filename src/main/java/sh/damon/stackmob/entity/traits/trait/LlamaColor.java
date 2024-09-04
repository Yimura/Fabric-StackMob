package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.LlamaEntity;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

@TraitMetadata(assignable = LlamaEntity.class, path = "llama-color")
public class LlamaColor implements Trait<LlamaEntity> {
    @Override
    public void applyTrait(LlamaEntity spawned, LlamaEntity dead) {
        spawned.setVariant(dead.getVariant());
    }

    @Override
    public boolean checkTrait(LlamaEntity first, LlamaEntity second) {
        return first.getVariant() == second.getVariant() && first.getCarpetColor() == second.getCarpetColor();
    }
}
