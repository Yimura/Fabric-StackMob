package sh.damon.stackmob;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sh.damon.stackmob.command.CommandManager;
import sh.damon.stackmob.config.Config;
import sh.damon.stackmob.entity.EntityManager;
import sh.damon.stackmob.entity.traits.TraitManager;

import java.lang.reflect.InvocationTargetException;

public class StackMob implements ModInitializer {
	public static final String MOD_ID = "stackmob";
	public static final String MOD_NAME = "StackMob";

    public final CommandManager commandManager = new CommandManager();
    public final EntityManager entityManager = new EntityManager();
    public final TraitManager traitManager = new TraitManager();

    private final static Logger LOGGER = LoggerFactory.getLogger(StackMob.class);

    private static StackMob instance;
    private Config config;

    @Override
    public void onInitialize() {
        StackMob.instance = this;

        this.config = Config.load();

        this.commandManager.registerAll();

        try {
            this.traitManager.registerAll();
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            LOGGER.error("Exception occurred while trying to register entity traits: {}", e.getMessage());
        }

        LOGGER.info( "Mod is ready.");
    }

    public static StackMob getInstance() {
        return instance;
    }

    public Config getConfig() {
        return config;
    }
}
