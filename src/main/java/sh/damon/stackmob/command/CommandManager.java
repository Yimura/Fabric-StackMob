package sh.damon.stackmob.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import sh.damon.stackmob.command.commands.CreateStackEntity;
import sh.damon.stackmob.command.commands.KillStackedEntity;

import java.util.HashSet;

public class CommandManager {
    private final HashSet<StackMobCommand> commands;

    public CommandManager() {
        this.commands = new HashSet<>();
    }

    /**
     * Register commands to the CommandDispatcher of the server
     * @param dispatcher The original instance coming from the server
     * @param isDedicated If the mod is running in dedicated environment or not
     */
    public void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean isDedicated) {
        for (StackMobCommand command : this.commands) {
            command.register(dispatcher);
        }
    }

    public void registerAll() {
        this.register(new CreateStackEntity());
        this.register(new KillStackedEntity());
    }

    private void register(final StackMobCommand command) {
        this.commands.add(command);
    }
}
