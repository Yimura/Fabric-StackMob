package sh.damon.stackmob.config;

import net.minecraft.util.Identifier;

public class SlimeConfig extends EntityConfig {
    public SlimeConfig() {
        super();
        setEntityId(Identifier.ofVanilla("slime"));
    }

    public boolean increaseSize = true;
}