package sh.damon.stackmob.entity.traits;

import net.minecraft.entity.LivingEntity;
import sh.damon.stackmob.entity.stackentity.StackEntity;
import sh.damon.stackmob.entity.traits.trait.*;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;

public class TraitManager {
    private final HashSet<Trait> traits;

    public TraitManager() {
        this.traits = new HashSet<>();
    }

    public void applyTraits(StackEntity spawned, StackEntity dead) {
        for (Trait trait : this.traits)
            if (this.isTraitApplicable(trait, spawned.getEntity()))
                trait.applyTrait(spawned.getEntity(), dead.getEntity());
    }

    public boolean checkTraits(StackEntity first, StackEntity second) {
        for (Trait trait : this.traits)
            if (this.isTraitApplicable(trait, first.getEntity()) && trait.checkTrait(first.getEntity(), second.getEntity()))
                return true;
        return false;
    }

    public void registerAll() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        this.register(Age.class);
        this.register(Bee.class);
        this.register(CatType.class);
        this.register(DrownedItem.class);
        this.register(FoxType.class);
        this.register(HorseColor.class);
        this.register(Leash.class);
        this.register(LlamaColor.class);
        this.register(LoveMode.class);
        this.register(MooshroomVariant.class);
        this.register(ParrotVariant.class);
        this.register(PiglinBaby.class);
        this.register(Potion.class);
        this.register(SheepColor.class);
        this.register(SheepShear.class);
        this.register(SlimeSize.class);
        this.register(TurtleHasEgg.class);
        this.register(ZoglinBaby.class);
        this.register(ZombieBaby.class);
    }

    private boolean isTraitApplicable(Trait trait, LivingEntity entity) {
        final TraitMetadata metadata = trait.getClass().getAnnotation(TraitMetadata.class);

        return metadata.assignable().isAssignableFrom(entity.getClass());
    }

    private void register(Class<? extends Trait> trait) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final TraitMetadata metadata = trait.getAnnotation(TraitMetadata.class);
        this.traits.add(trait.getDeclaredConstructor().newInstance());
    }
}
