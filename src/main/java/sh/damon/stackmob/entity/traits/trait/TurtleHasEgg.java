package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.nbt.NbtCompound;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

@TraitMetadata(assignable = TurtleEntity.class, path = "turtle-has-egg")
public class TurtleHasEgg implements Trait<TurtleEntity> {
    @Override
    public void applyTrait(TurtleEntity spawned, TurtleEntity dead) {
        NbtCompound nbt = new NbtCompound();

        dead.writeCustomDataToNbt(nbt);
        spawned.readCustomDataFromNbt(nbt);
    }

    @Override
    public boolean checkTrait(TurtleEntity first, TurtleEntity second) {
        return first.hasEgg() == second.hasEgg();
    }
}
