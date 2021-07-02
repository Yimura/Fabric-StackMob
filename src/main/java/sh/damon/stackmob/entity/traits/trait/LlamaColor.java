package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.LlamaEntity;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

@TraitMetadata(assignable = LlamaEntity.class, path = "llama-color")
public class LlamaColor implements Trait {
    @Override
    public void applyTrait(LivingEntity spawned, LivingEntity dead) {
        ((LlamaEntity) spawned).setVariant(((LlamaEntity) dead).getVariant());
    }

    @Override
    public boolean checkTrait(LivingEntity first, LivingEntity second) {
        return ((LlamaEntity) first).getVariant() != ((LlamaEntity) second).getVariant();
    }
}
