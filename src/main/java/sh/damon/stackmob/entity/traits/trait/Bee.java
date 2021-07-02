package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.nbt.NbtCompound;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

@TraitMetadata(assignable = BeeEntity.class, path = "bee")
public class Bee implements Trait {
    @Override
    public void applyTrait(LivingEntity spawned, LivingEntity dead) {
        NbtCompound nbt = new NbtCompound();
        ((BeeEntity) dead).writeCustomDataToNbt(nbt);

        ((BeeEntity) spawned).readCustomDataFromNbt(nbt);
    }

    @Override
    public boolean checkTrait(LivingEntity first, LivingEntity second) {
        return ((BeeEntity) first).hasNectar() != ((BeeEntity) second).hasNectar();
    }
}
