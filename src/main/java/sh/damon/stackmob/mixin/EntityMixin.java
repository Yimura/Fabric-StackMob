package sh.damon.stackmob.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sh.damon.stackmob.StackMob;
import sh.damon.stackmob.entity.StackEntity;

import java.util.Random;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(at = @At("HEAD"), method = "dropStack(Lnet/minecraft/item/ItemStack;F)Lnet/minecraft/entity/ItemEntity;")
    public void onDroppedItem(ItemStack stack, float yOffset, CallbackInfoReturnable<ItemEntity> cir) {
        Entity entity = (Entity) (Object) this;

        if (!(entity instanceof LivingEntity) || !(entity instanceof ChickenEntity || entity instanceof TurtleEntity)) return;
        if (!(stack.isOf(Items.EGG) || stack.isOf(Items.SCUTE))) return;

        StackMob sm = StackMob.getInstance();
        if (!sm.entityManager.isRegistered((LivingEntity) entity)) return;
        StackEntity stackEntity = sm.entityManager.getStackedEntity((LivingEntity) entity);

        double multiplier = .4 + (.8 - .4) * new Random().nextDouble();
        int itemCount = (int) Math.round(stackEntity.getSize() * multiplier);

        stack.setCount(itemCount);
    }
}
