package sh.damon.stackmob.entity.traits;

import net.minecraft.entity.mob.MobEntity;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface TraitMetadata {
    Class<?> assignable();
    String path();
}
