package sh.damon.stackmob.mixin;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sh.damon.stackmob.StackMob;
import sh.damon.stackmob.entity.StackEntity;

@Mixin(AnimalEntity.class)
public class AnimalEntityMixin {
    @Inject(at = @At("HEAD"), method = "interactMob")
    public void onInteractWithMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        AnimalEntity animal = (AnimalEntity) (Object) this;

        ItemStack stack = player.getStackInHand(hand);
        if (!animal.isBreedingItem(stack) || animal.isBaby() || animal.getBreedingAge() != 0) return;

        StackMob sm = StackMob.getInstance();
        if (!sm.entityManager.isRegistered(animal)) return;

        StackEntity parent = sm.entityManager.getStackedEntity(animal);
        int kids = Math.min(parent.getSize(), stack.getCount()) / 2;
        stack.setCount(stack.getCount() - kids * 2);

        animal.world.sendEntityStatus(animal, (byte)18);
        animal.setBreedingAge(6000);
        animal.resetLoveTicks();

        AnimalEntity baby = (AnimalEntity) parent.duplicate();

        sm.entityManager.register(baby)
            .setSize(kids);

        sm.traitManager.applyTraits(baby, animal);

        baby.setBaby(true);
        baby.setPosition(animal.getPos());

        animal.world.spawnEntity(baby);

        player.increaseStat(Stats.ANIMALS_BRED, kids);
        Criteria.BRED_ANIMALS.trigger(
            player instanceof ServerPlayerEntity ? (ServerPlayerEntity)player : null,
            animal,
            animal,
            baby
        );
    }
}
