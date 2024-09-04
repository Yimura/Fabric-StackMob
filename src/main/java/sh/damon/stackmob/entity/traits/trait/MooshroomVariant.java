package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.nbt.NbtCompound;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

@TraitMetadata(assignable = MooshroomEntity.class, path = "mooshroom-variant")
public class MooshroomVariant implements Trait<MooshroomEntity> {
    @Override
    public void applyTrait(MooshroomEntity spawned, MooshroomEntity dead) {
        NbtCompound nbt = new NbtCompound();

        dead.writeCustomDataToNbt(nbt);
        spawned.readCustomDataFromNbt(nbt);
    }

    @Override
    public boolean checkTrait(MooshroomEntity first, MooshroomEntity second) {
        return first.getVariant() == second.getVariant();
    }
}
