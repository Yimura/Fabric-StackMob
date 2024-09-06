package sh.damon.stackmob.config;

import net.minecraft.util.Identifier;

public class EntityConfig {
    public Identifier entityId = Identifier.ofVanilla("sheep");
    public boolean stackable = true;
    public boolean babyStacking = false;
    public int maxStackSize = 2048;

    protected void setEntityId(Identifier entityId) {
        this.entityId = entityId;
    }
}