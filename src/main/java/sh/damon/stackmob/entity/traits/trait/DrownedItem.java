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
public class DrownedItem implements Trait<DrownedEntity> {
    private static final List<EquipmentSlot> HAND_SLOTS = Arrays.asList(EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND);
    private static final List<Item> DROWNED_MATERIALS = Arrays.asList(Items.NAUTILUS_SHELL, Items.TRIDENT);

    @Override
    public void applyTrait(DrownedEntity spawned, DrownedEntity dead) {
        for (EquipmentSlot equipmentSlot : HAND_SLOTS) {
            ItemStack item = dead.getEquippedStack(equipmentSlot);

            if (DROWNED_MATERIALS.contains(item.getItem())) {
                spawned.equipStack(equipmentSlot, item);
            }
        }
    }

    @Override
    public boolean checkTrait(DrownedEntity first, DrownedEntity second) {
        for (EquipmentSlot equipmentSlot : HAND_SLOTS) {
            ItemStack oldItemStack = first.getEquippedStack(equipmentSlot);
            ItemStack newItemStack = second.getEquippedStack(equipmentSlot);

            if (!oldItemStack.equals(newItemStack)) continue;

            if (DROWNED_MATERIALS.contains(oldItemStack.getItem())) return false;
        }
        return true;
    }
}
