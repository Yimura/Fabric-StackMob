package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.nbt.NbtCompound;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

@TraitMetadata(assignable = HorseEntity.class, path = "horse-color")
public class HorseColor implements Trait {
    @Override
    public void applyTrait(LivingEntity spawned, LivingEntity dead) {
        NbtCompound nbt = new NbtCompound();

        ((HorseEntity) dead).writeCustomDataToNbt(nbt);
        ((HorseEntity) spawned).readCustomDataFromNbt(nbt);
    }

    @Override
    public boolean checkTrait(LivingEntity first, LivingEntity second) {
        HorseEntity firstHorse = (HorseEntity) first;
        HorseEntity secondHorse = (HorseEntity) second;

        return firstHorse.getColor() == secondHorse.getColor() &&
                firstHorse.getArmorType() == secondHorse.getArmorType();
    }
}
