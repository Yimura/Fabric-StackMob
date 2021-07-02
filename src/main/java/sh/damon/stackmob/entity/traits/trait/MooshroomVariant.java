package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.nbt.NbtCompound;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

@TraitMetadata(assignable = MooshroomEntity.class, path = "mooshroom-variant")
public class MooshroomVariant implements Trait {
    @Override
    public void applyTrait(LivingEntity spawned, LivingEntity dead) {
        NbtCompound nbt = new NbtCompound();

        ((MooshroomEntity) dead).writeCustomDataToNbt(nbt);
        ((MooshroomEntity) spawned).readCustomDataFromNbt(nbt);
    }

    @Override
    public boolean checkTrait(LivingEntity first, LivingEntity second) {
        return ((MooshroomEntity) first).getMooshroomType() != ((MooshroomEntity) second).getMooshroomType();
    }
}
