package sh.damon.stackmob.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Locale;

public class StackEntity {
    protected final LivingEntity owner;

    private final int maxSize = 2048;
    private int size = 1;

    private boolean removed = false;

    public StackEntity(LivingEntity entity) {
        this.owner = entity;
    }

    public LivingEntity duplicate() {
        return (LivingEntity) this.owner.getType().create(this.owner.world);
    }

    public LivingEntity getEntity() {
        return this.owner;
    }

    public String getName() {
        return Registry.ENTITY_TYPE.getId(this.owner.getType()).getPath().toUpperCase(Locale.ROOT);
    }

    public int getSize() {
        return this.size;
    }

    public boolean isMaxStackSize() {
        return this.maxSize <= this.size;
    }

    public boolean isUnableToStack() {
        return this.owner.isDead() || this.owner.isRemoved() || this.isMaxStackSize() || this.removed;
    }

    public StackEntity merge(StackEntity other) {
        boolean mergeOther = other.getSize() >= this.getSize();

        final StackEntity smallest = mergeOther ? other : this;
        final StackEntity biggest = mergeOther ? this : other;

        final int totalSize = smallest.getSize() + biggest.getSize();
        final int maxSize = this.maxSize;

        if (totalSize > maxSize) {
            smallest.setSize(totalSize - maxSize);
            biggest.setSize(maxSize);

            return null;
        }

        biggest.setSize(totalSize);

        return smallest;
    }

    public void setSize(final int newSize) {
        this.size = newSize;

        this.owner.setCustomName(newSize == 1 ?
                null :
                Text.of(this.getName() + " ("+ newSize + ")"));
    }

    public void setRemoved() {
        this.removed = true;
    }
}
