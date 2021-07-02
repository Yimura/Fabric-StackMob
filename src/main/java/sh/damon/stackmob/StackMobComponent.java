package sh.damon.stackmob;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import sh.damon.stackmob.entity.stackentity.BaseStackEntity;
import sh.damon.stackmob.entity.stackentity.StackSize;

public class StackMobComponent implements EntityComponentInitializer {
    public static final ComponentKey<StackSize> STACK_SIZE =
            ComponentRegistry.getOrCreate(new Identifier("stackmob", "stack_size"), StackSize.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(LivingEntity.class, STACK_SIZE, e -> new BaseStackEntity());
    }
}
