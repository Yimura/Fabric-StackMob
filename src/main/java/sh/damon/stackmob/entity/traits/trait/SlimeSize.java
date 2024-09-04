package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.nbt.NbtCompound;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

@TraitMetadata(assignable = SlimeEntity.class, path = "slime-size")
public class SlimeSize implements Trait<SlimeEntity> {
    @Override
    public void applyTrait(SlimeEntity spawned, SlimeEntity dead) {
        NbtCompound nbt = new NbtCompound();

        dead.writeCustomDataToNbt(nbt);
        spawned.readCustomDataFromNbt(nbt);
    }

    @Override
    public boolean checkTrait(SlimeEntity first, SlimeEntity second) {
        return first.getSize() == second.getSize();
    }
}
