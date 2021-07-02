package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.nbt.NbtCompound;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

@TraitMetadata(assignable = SlimeEntity.class, path = "slime-size")
public class SlimeSize implements Trait {
    @Override
    public void applyTrait(LivingEntity spawned, LivingEntity dead) {
        NbtCompound nbt = new NbtCompound();

        ((SlimeEntity) dead).writeCustomDataToNbt(nbt);
        ((SlimeEntity) spawned).readCustomDataFromNbt(nbt);
    }

    @Override
    public boolean checkTrait(LivingEntity first, LivingEntity second) {
        return ((SlimeEntity) first).getSize() != ((SlimeEntity) second).getSize();
    }
}
