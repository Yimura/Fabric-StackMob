package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.LlamaEntity;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

@TraitMetadata(assignable = LlamaEntity.class, path = "llama-color")
public class LlamaColor implements Trait {
    @Override
    public void applyTrait(LivingEntity spawned, LivingEntity dead) {
        LlamaEntity spawnedLama = (LlamaEntity) spawned;
        LlamaEntity deadLama = (LlamaEntity) dead;

        spawnedLama.setVariant(deadLama.getVariant());
    }

    @Override
    public boolean checkTrait(LivingEntity first, LivingEntity second) {
        LlamaEntity firstLama = (LlamaEntity) first;
        LlamaEntity secondLama = (LlamaEntity) second;

        return firstLama.getVariant() == secondLama.getVariant() && firstLama.getCarpetColor() == secondLama.getCarpetColor();
    }
}
