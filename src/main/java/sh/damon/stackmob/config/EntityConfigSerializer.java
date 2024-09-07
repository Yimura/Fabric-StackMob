package sh.damon.stackmob.config;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializer;

public class EntityConfigSerializer implements JsonSerializer<EntityConfig> {
    @Override
    public JsonElement serialize(EntityConfig src, java.lang.reflect.Type typeOfSrc, com.google.gson.JsonSerializationContext context) {
        return new Gson().toJsonTree(src);
    }
}
