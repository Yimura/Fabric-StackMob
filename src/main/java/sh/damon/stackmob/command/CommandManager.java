package sh.damon.stackmob.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.ServerCommandSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sh.damon.stackmob.command.commands.CreateStackEntity;
import sh.damon.stackmob.command.commands.KillStackedEntity;

import java.util.HashSet;

public class CommandManager {
    private final HashSet<ICommand> commands;
    private final Logger LOGGER = LoggerFactory.getLogger(CommandManager.class);

    public CommandManager() {
        this.commands = new HashSet<>();
    }

    /**
     * Register commands to the CommandDispatcher of the server
     * @param dispatcher The original instance coming from the server
     * @param isDedicated If the mod is running in dedicated environment or not
     */
    public void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, boolean isDedicated) {
        for (ICommand command : this.commands) {
            command.register(dispatcher, registryAccess, isDedicated);
        }

        LOGGER.debug("Commands have been registered with internal server.");
    }

    public void registerAll() {
        this.register(new CreateStackEntity());
        this.register(new KillStackedEntity());

        LOGGER.debug("Commands registered.");
    }

    private void register(final ICommand command) {
        this.commands.add(command);
    }
}
