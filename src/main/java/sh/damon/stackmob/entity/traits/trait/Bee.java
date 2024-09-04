package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.nbt.NbtCompound;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

@TraitMetadata(assignable = BeeEntity.class, path = "bee")
public class Bee implements Trait<BeeEntity> {
    @Override
    public void applyTrait(BeeEntity spawned, BeeEntity dead) {
        NbtCompound nbt = new NbtCompound();
        dead.writeCustomDataToNbt(nbt);

        spawned.readCustomDataFromNbt(nbt);
    }

    @Override
    public boolean checkTrait(BeeEntity first, BeeEntity second) {
        return first.hasNectar() == second.hasNectar();
    }
}
