package sh.damon.stackmob.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sh.damon.stackmob.StackMob;
import sh.damon.stackmob.entity.StackEntity;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    private static final String STACK_SIZE = "StackSize";

    @Inject(at = @At("HEAD"), method = "onDeath")
    public void onDeath(DamageSource source, CallbackInfo info) {
        LivingEntity died = (LivingEntity) (Object) this;

        StackMob sm = StackMob.getInstance();
        if (!sm.entityManager.isRegistered(died)) return;

        StackEntity stackEntity = sm.entityManager.getStackedEntity(died);
        sm.entityManager.unregisterStackedEntity(stackEntity);

        int size = stackEntity.getSize();
        if (size == 1) {
            sm.entityManager.unregisterStackedEntity(stackEntity);

            return;
        }

        LivingEntity spawned = stackEntity.duplicate();

        sm.traitManager.applyTraits(spawned, died);

        spawned.setPosition(died.getPos());
        died.world.spawnEntity(spawned);

        stackEntity = sm.entityManager.register(spawned);
        stackEntity.setSize(size - 1);
    }

    @Inject(at = @At("RETURN"), method = "readCustomDataFromNbt")
    public void readFromNbt(NbtCompound nbt, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (!nbt.contains(STACK_SIZE)) return;

        StackMob sm = StackMob.getInstance();

        StackEntity stackEntity = sm.entityManager.register(entity);
        stackEntity.setSize(nbt.getInt(STACK_SIZE));
    }

    @Inject(at = @At("RETURN"), method = "writeCustomDataToNbt")
    public void writeToNbt(NbtCompound nbt, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;

        StackMob sm = StackMob.getInstance();
        if (!sm.entityManager.isRegistered(entity)) return;
        StackEntity stackEntity = sm.entityManager.getStackedEntity(entity);

        nbt.putInt(STACK_SIZE, stackEntity.getSize());
    }
}
