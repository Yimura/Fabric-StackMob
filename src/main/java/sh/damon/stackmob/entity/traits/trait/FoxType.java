package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.nbt.NbtCompound;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

@TraitMetadata(assignable = FoxEntity.class, path = "fox-type")
public class FoxType implements Trait<FoxEntity> {
    @Override
    public void applyTrait(FoxEntity spawned, FoxEntity dead) {
        NbtCompound nbt = new NbtCompound();
        dead.writeCustomDataToNbt(nbt);

        spawned.readCustomDataFromNbt(nbt);
    }

    @Override
    public boolean checkTrait(FoxEntity first, FoxEntity second) {
        return first.getVariant() == second.getVariant();
    }
}
