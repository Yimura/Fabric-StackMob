package sh.damon.stackmob.config;

import net.minecraft.util.Identifier;

public class EntityConfig {
    public Identifier entityId = Identifier.ofVanilla("sheep");
    public boolean stackable = true;
    public boolean babyStacking = false;
    public int maxStackSize = 2048;

    public EntityConfig() {}

    public EntityConfig(Identifier entityId) {
        this.entityId = entityId;
    }

    public void setEntityId(Identifier entityId) {
        this.entityId = entityId;
    }
}