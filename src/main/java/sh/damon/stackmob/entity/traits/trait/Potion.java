package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

@TraitMetadata(assignable = MobEntity.class, path = "potion-effect")
public class Potion implements Trait {
    @Override
    public void applyTrait(LivingEntity spawned, LivingEntity dead) {
        for (StatusEffectInstance effect : dead.getActiveStatusEffects().values())
            spawned.addStatusEffect(effect);
    }

    @Override
    public boolean checkTrait(LivingEntity first, LivingEntity second) {
        return false;
    }
}
