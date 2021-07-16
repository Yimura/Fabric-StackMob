package sh.damon.stackmob.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;

public interface StackMobCommand {
    /**
     * @return The label of the command
     */
    void register(CommandDispatcher<ServerCommandSource> dispatcher);
}
