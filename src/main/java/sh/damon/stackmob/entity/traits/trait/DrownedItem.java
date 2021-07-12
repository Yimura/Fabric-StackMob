package sh.damon.stackmob.entity.traits.trait;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import sh.damon.stackmob.entity.traits.Trait;
import sh.damon.stackmob.entity.traits.TraitMetadata;

import java.util.Arrays;
import java.util.List;

@TraitMetadata(assignable = DrownedEntity.class, path = "drowned-hand-items")
public class DrownedItem implements Trait {
    private static List<EquipmentSlot> HAND_SLOTS = Arrays.asList(EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND);
    private static List<Item> DROWNED_MATERIALS = Arrays.asList(Items.NAUTILUS_SHELL, Items.TRIDENT);

    @Override
    public void applyTrait(LivingEntity spawned, LivingEntity dead) {
        DrownedEntity oriDrowned = (DrownedEntity) dead;
        DrownedEntity spawnDrowned = (DrownedEntity) spawned;
        for (EquipmentSlot equipmentSlot : HAND_SLOTS) {
            ItemStack item = oriDrowned.getEquippedStack(equipmentSlot);

            if (DROWNED_MATERIALS.contains(item.getItem())) {
                spawnDrowned.equipStack(equipmentSlot, item);
            }
        }
    }

    @Override
    public boolean checkTrait(LivingEntity first, LivingEntity second) {
        DrownedEntity oldDrowned = (DrownedEntity) first;
        DrownedEntity newDrowned = (DrownedEntity) second;

        for (EquipmentSlot equipmentSlot : HAND_SLOTS) {
            ItemStack oldItemStack = oldDrowned.getEquippedStack(equipmentSlot);
            ItemStack newItemStack = newDrowned.getEquippedStack(equipmentSlot);

            if (!oldItemStack.isItemEqual(newItemStack)) continue;

            if (DROWNED_MATERIALS.contains(oldItemStack.getItem())) return false;
        }
        return true;
    }
}
