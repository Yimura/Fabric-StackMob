package sh.damon.stackmob.command.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.NbtCompoundArgumentType;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import sh.damon.stackmob.StackMob;
import sh.damon.stackmob.command.StackMobCommand;
import sh.damon.stackmob.entity.StackEntity;

import java.util.Objects;

import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class CreateStackEntity implements StackMobCommand {
    @Override
    public void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
            literal("sm").then(literal("create").then(
                argument("type", EntityArgumentType.entity())
                .suggests(SuggestionProviders.SUMMONABLE_ENTITIES).then(
                    argument("stack_size", integer(2, 2048)).executes(this)
                )
            ))
        );

        dispatcher.register(
            literal("sm").then(literal("create").then(
                argument("type", EntityArgumentType.entity())
                .suggests(SuggestionProviders.SUMMONABLE_ENTITIES).then(
                    argument("stack_size", integer(2, 2048)).then(
                        argument("nbt", NbtCompoundArgumentType.nbtCompound()).executes(this)
                    )
                )
            ))
        );
    }

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Entity id = EntityArgumentType.getEntity(context, "type");

        NbtCompound nbt;
        try {
            nbt = NbtCompoundArgumentType.getNbtCompound(context,"nbt");
        }
        catch (IllegalArgumentException e) {
            nbt = new NbtCompound();
        }
        nbt.putString("id", id.toString());

        final ServerCommandSource source = context.getSource();
        final ServerWorld world = source.getWorld();

        // get position of the player that requested the stackmob
        final BlockPos spawnPos = Objects.requireNonNull(source.getEntity()).getBlockPos();

        Entity entity = EntityType.loadEntityWithPassengers(nbt, world, ent -> {
            ent.refreshPositionAndAngles(spawnPos, ent.getYaw(), ent.getPitch());

            return ent;
        });

        if (entity == null)
            throw new SimpleCommandExceptionType(new LiteralMessage("Failed to create entity")).create();

        if (entity instanceof MobEntity)
            ((MobEntity) entity).initialize(world, world.getLocalDifficulty(spawnPos), SpawnReason.COMMAND, null);

        if (!world.spawnNewEntityAndPassengers(entity))
            throw new SimpleCommandExceptionType(new LiteralMessage("Failed to create entity, UUID duplicate in registry.")).create();

        final StackMob sm = StackMob.getInstance();
        StackEntity stackEntity;
        if (sm.entityManager.isRegistered((LivingEntity) entity))
            stackEntity = sm.entityManager.getStackedEntity((LivingEntity) entity);
        else
            stackEntity = sm.entityManager.register((LivingEntity) entity);

        int size = getInteger(context, "stack_size");
        if (size < 1 || size > stackEntity.getMaxSize())
            throw new SimpleCommandExceptionType(new LiteralMessage("Stack size is large than the maximum stack size or smaller than 1.")).create();

        stackEntity.setSize(size);

        return 1;
    }
}
