package sh.damon.stackmob.command.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import sh.damon.stackmob.StackMob;
import sh.damon.stackmob.command.StackMobCommand;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class KillStackedEntity implements StackMobCommand {
    @Override
    public void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
            literal("sm").then(literal("kill").then(argument("target", EntityArgumentType.entity()).executes(this)))
        );
    }

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Entity ent = EntityArgumentType.getEntity(context, "target");
        if (!(ent instanceof LivingEntity)) throw new SimpleCommandExceptionType(new LiteralText("Invalid entity")).create();

        StackMob sm = StackMob.getInstance();

        if (!sm.entityManager.isRegistered((LivingEntity) ent)) throw new SimpleCommandExceptionType(new LiteralText("Entity is not a StackEntity.")).create();

        sm.entityManager.unregisterStackedEntity(
            sm.entityManager.getStackedEntity((LivingEntity) ent)
        );
        ent.kill();

        context.getSource().sendFeedback(new LiteralText("Successfully removed StackEntity."), false);

        return 0;
    }
}
