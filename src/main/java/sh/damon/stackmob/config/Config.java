package sh.damon.stackmob.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Config {
    private static final EntityConfigSerializer EntityConfigSerializer = new EntityConfigSerializer();
    private static final EntityConfigDeserializer EntityConfigDeserializer = new EntityConfigDeserializer();

    private static final Gson GSON = new GsonBuilder().registerTypeHierarchyAdapter(EntityConfig.class, EntityConfigSerializer).registerTypeHierarchyAdapter(EntityConfig.class, EntityConfigDeserializer).setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "stackmob.json");

    public boolean stackingEnabled = true;

    public ArrayList<? extends EntityConfig> entities = new ArrayList<>() { {
        add(new EntityConfig());
        add(new SlimeConfig());
    } };

    public static Config load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                return GSON.fromJson(reader, Config.class);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to load config file");
            }
        } else {
            var config = new Config();
            writeFile(config);
            return config;
        }
    }

    public void save() {
        writeFile(this);
    }

    private static void writeFile(Config config) {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

