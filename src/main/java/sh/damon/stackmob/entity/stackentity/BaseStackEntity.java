package sh.damon.stackmob.entity.stackentity;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.minecraft.nbt.NbtCompound;

public class BaseStackEntity implements StackSize, ComponentV3 {
    protected int stackSize;
    private final String STACK_SIZE = "stack_size";

    public BaseStackEntity() {
        this(1);
    }

    public BaseStackEntity(int initialSize) {
        this.stackSize = initialSize;
    }

    @Override
    public int getSize() {
        return this.stackSize;
    }

    @Override
    public void setSize(int newSize) {
        this.stackSize = newSize;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.stackSize = tag.getInt(STACK_SIZE);
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt(STACK_SIZE, this.stackSize);
    }
}
