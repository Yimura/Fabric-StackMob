package sh.damon.stackmob.mixin;

import com.google.common.collect.Maps;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sh.damon.stackmob.StackMob;
import sh.damon.stackmob.entity.StackEntity;

import java.util.Map;
import java.util.Random;

@Mixin(SheepEntity.class)
public class SheepEntityMixin {
    private static final Map DROPS;

    @Inject(at = @At("HEAD"), method = "interactMob", cancellable = true)
    public void onSheared(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        SheepEntity sheep = (SheepEntity) (Object) this;

        StackMob sm = StackMob.getInstance();
        if (!sm.entityManager.isRegistered(sheep)) return;

        StackEntity stackEntity = sm.entityManager.getStackedEntity(sheep);
        if (stackEntity.getSize() <= 1) return;

        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(Items.SHEARS) && !sheep.world.isClient && sheep.isShearable()) {
            SheepEntityMixin.shear(sheep, stackEntity.getSize());

            sheep.emitGameEvent(GameEvent.SHEAR, player);

            itemStack.damage(stackEntity.getSize(), player, (playerx) -> {
                playerx.sendToolBreakStatus(hand);
            });

            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }

    private static void shear(SheepEntity sheep, int size) {
        sheep.world.playSoundFromEntity((PlayerEntity)null, sheep, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.PLAYERS, 1.0F, 1.0F);
        sheep.setSheared(true);

        final Random random = new Random();

        int i = (1 + random.nextInt(3)) * size;
        for(int j = 0; j < i; ++j) {
            ItemEntity itemEntity = sheep.dropItem((ItemConvertible)DROPS.get(sheep.getColor()), 1);
            if (itemEntity != null) {
                itemEntity.setVelocity(itemEntity.getVelocity().add((double)((random.nextFloat() - random.nextFloat()) * 0.1F), (double)(random.nextFloat() * 0.05F), (double)((random.nextFloat() - random.nextFloat()) * 0.1F)));
            }
        }
    }

    static {
        DROPS = Util.make(Maps.newEnumMap(DyeColor.class), (map) -> {
            map.put(DyeColor.WHITE, Blocks.WHITE_WOOL);
            map.put(DyeColor.ORANGE, Blocks.ORANGE_WOOL);
            map.put(DyeColor.MAGENTA, Blocks.MAGENTA_WOOL);
            map.put(DyeColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_WOOL);
            map.put(DyeColor.YELLOW, Blocks.YELLOW_WOOL);
            map.put(DyeColor.LIME, Blocks.LIME_WOOL);
            map.put(DyeColor.PINK, Blocks.PINK_WOOL);
            map.put(DyeColor.GRAY, Blocks.GRAY_WOOL);
            map.put(DyeColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_WOOL);
            map.put(DyeColor.CYAN, Blocks.CYAN_WOOL);
            map.put(DyeColor.PURPLE, Blocks.PURPLE_WOOL);
            map.put(DyeColor.BLUE, Blocks.BLUE_WOOL);
            map.put(DyeColor.BROWN, Blocks.BROWN_WOOL);
            map.put(DyeColor.GREEN, Blocks.GREEN_WOOL);
            map.put(DyeColor.RED, Blocks.RED_WOOL);
            map.put(DyeColor.BLACK, Blocks.BLACK_WOOL);
        });
    }
}
