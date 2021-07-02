package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.nbt.NbtCompound;
import sh.damon.stackmob.entity.traits.Trait;

public class TurtleHasEgg implements Trait {
    @Override
    public void applyTrait(LivingEntity spawned, LivingEntity dead) {
        NbtCompound nbt = new NbtCompound();

        ((TurtleEntity) dead).writeCustomDataToNbt(nbt);
        ((TurtleEntity) spawned).readCustomDataFromNbt(nbt);
    }

    @Override
    public boolean checkTrait(LivingEntity first, LivingEntity second) {
        return ((TurtleEntity) first).hasEgg() != ((TurtleEntity) second).hasEgg();
    }
}
