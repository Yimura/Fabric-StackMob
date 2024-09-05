package sh.damon.stackmob.command.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import sh.damon.stackmob.StackMob;
import sh.damon.stackmob.command.ICommand;

import java.util.Collection;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class KillStackedEntity implements ICommand {
    @Override
    public void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, boolean isDedicated) {
        dispatcher.register(
            literal("sm")
                .then(literal("kill")
                    .then(argument("targets", EntityArgumentType.entities()).executes(this))
                )
            );
    }

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Collection<? extends Entity> entities = EntityArgumentType.getEntities(context, "targets");

        final StackMob sm = StackMob.getInstance();
        for (Entity ent : entities) {
            if (!(ent instanceof LivingEntity))
                continue;

            if (!sm.entityManager.isRegistered((LivingEntity) ent))
                continue;

            sm.entityManager.unregisterStackedEntity(
                    sm.entityManager.getStackedEntity((LivingEntity) ent)
            );
            ent.kill();
        }

        context.getSource().sendFeedback(() -> Text.literal("Successfully remove stacked entities."), false);

        return SINGLE_SUCCESS;
    }
}
