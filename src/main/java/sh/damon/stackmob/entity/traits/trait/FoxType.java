package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.nbt.NbtCompound;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

@TraitMetadata(assignable = FoxEntity.class, path = "fox-type")
public class FoxType implements Trait {
    @Override
    public void applyTrait(LivingEntity spawned, LivingEntity dead) {
        NbtCompound nbt = new NbtCompound();
        ((FoxEntity) dead).writeCustomDataToNbt(nbt);

        ((FoxEntity) spawned).readCustomDataFromNbt(nbt);
    }

    @Override
    public boolean checkTrait(LivingEntity first, LivingEntity second) {
        return ((FoxEntity) first).getFoxType() == ((FoxEntity) second).getFoxType();
    }
}
