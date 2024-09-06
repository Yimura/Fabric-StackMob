package sh.damon.stackmob.config;

import com.google.gson.*;

import java.lang.reflect.Type;

public class EntityConfigDeserializer implements JsonDeserializer<EntityConfig> {
    @Override
    public EntityConfig deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();
        JsonObject entityId = jsonObject.get("entityId").getAsJsonObject();
        String namespace = entityId.get("namespace").getAsString();
        String path = entityId.get("path").getAsString();

        if (namespace.equalsIgnoreCase("minecraft") && path.equalsIgnoreCase("slime")) {
            return new Gson().fromJson(json, SlimeConfig.class);
        }

        return new Gson().fromJson(json, EntityConfig.class);
    }
}
