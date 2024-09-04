package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.nbt.NbtCompound;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

@TraitMetadata(assignable = HorseEntity.class, path = "horse-color")
public class HorseColor implements Trait<HorseEntity> {
    @Override
    public void applyTrait(HorseEntity spawned, HorseEntity dead) {
        NbtCompound nbt = new NbtCompound();

        dead.writeCustomDataToNbt(nbt);
        spawned.readCustomDataFromNbt(nbt);
    }

    @Override
    public boolean checkTrait(HorseEntity first, HorseEntity second) {
        return first.getVariant() == second.getVariant() &&
                first.getBodyArmor().equals(second.getBodyArmor());
    }
}
