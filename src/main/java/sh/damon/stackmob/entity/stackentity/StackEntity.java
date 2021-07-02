package sh.damon.stackmob.entity.stackentity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;

public class StackEntity extends BaseStackEntity {
    protected final LivingEntity owner;

    private final int maxSize = 2048;
    private int size = 1;

    private boolean removed = false;

    public StackEntity(LivingEntity entity) {
        this.owner = entity;
    }

    public boolean canStack() {
        return this.owner.isAlive() && !this.isMaxStackSize() && !this.removed;
    }

    public LivingEntity getEntity() {
        return this.owner;
    }

    public boolean isMaxStackSize() {
        return this.maxSize <= this.size;
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
        super.setSize(newSize);

        this.owner.setCustomName(Text.of(this.owner.getName() + " ("+ newSize + ")"));
    }

    public void setRemoved() {
        this.removed = true;
    }
}
