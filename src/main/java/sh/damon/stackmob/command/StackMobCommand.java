package sh.damon.stackmob.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;

public interface StackMobCommand extends Command<ServerCommandSource> {
    /**
     * @return The label of the command
     */
    void register(CommandDispatcher<ServerCommandSource> dispatcher);
}
