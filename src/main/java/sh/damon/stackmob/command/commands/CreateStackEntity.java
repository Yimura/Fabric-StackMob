package sh.damon.stackmob.command.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.argument.EntitySummonArgumentType;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import sh.damon.stackmob.StackMob;
import sh.damon.stackmob.command.StackMobCommand;
import sh.damon.stackmob.entity.StackEntity;

import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class CreateStackEntity implements StackMobCommand {
    @Override
    public void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
            literal("sm")
                .then(literal("create")
                .then(
                    argument("type", EntitySummonArgumentType.entitySummon()).suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                    .then(
                        argument("stack_size", integer()).executes(this)
                    )
                )
            )
        );
    }

    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Identifier id = EntitySummonArgumentType.getEntitySummon(context, "type");

        NbtCompound nbt = new NbtCompound();
        nbt.putString("id", id.toString());

        final ServerCommandSource source = context.getSource();
        final ServerWorld world = source.getWorld();

        final BlockPos spawnPos = new BlockPos(source.getPosition());

        Entity entity = EntityType.loadEntityWithPassengers(nbt, world, ent -> {
            ent.refreshPositionAndAngles(spawnPos, ent.getYaw(), ent.getPitch());

            return ent;
        });

        if (entity == null)
            throw new SimpleCommandExceptionType(new LiteralText("Failed to create entity")).create();

        if (entity instanceof MobEntity)
            ((MobEntity) entity).initialize(world, world.getLocalDifficulty(spawnPos), SpawnReason.COMMAND, (EntityData) null, (NbtCompound) null);

        if (!world.shouldCreateNewEntityWithPassenger(entity))
            throw new SimpleCommandExceptionType(new LiteralText("Failed to create entity, UUID duplicate in registry.")).create();

        final StackMob sm = StackMob.getInstance();
        StackEntity stackEntity;
        if (sm.entityManager.isRegistered((LivingEntity) entity))
            stackEntity = sm.entityManager.getStackedEntity((LivingEntity) entity);
        else
            stackEntity = sm.entityManager.register((LivingEntity) entity);

        int size = getInteger(context, "stack_size");
        if (size < 1 || size > stackEntity.getMaxSize())
            throw new SimpleCommandExceptionType(new LiteralText("Stack size is large than the maximum stack size or smaller than 1.")).create();

        stackEntity.setSize(size);

        return 1;
    }
}
