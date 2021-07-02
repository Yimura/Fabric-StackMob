package sh.damon.stackmob.entity.traits;

import net.minecraft.entity.LivingEntity;

public interface Trait {
    /**
     * Copy the traits of the dead entity to that of the newly spawned entity.
     * @param spawned the entity that was spawned to replace it.
     * @param dead the entity that died.
     */
    void applyTrait(LivingEntity spawned, LivingEntity dead);

    /**
     * Check if two entities have the same entity specific traits (eg. sheep colour, villager profession)
     * @param first the initial entity.
     * @param second the other entity the first should stack with
     * @return whether these two entities should stack.
     */
    boolean checkTrait(LivingEntity first, LivingEntity second);
}
