package sh.damon.stackmob.command.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.NbtCompoundArgumentType;
import net.minecraft.command.argument.RegistryEntryReferenceArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sh.damon.stackmob.StackMob;
import sh.damon.stackmob.command.ICommand;
import sh.damon.stackmob.entity.StackEntity;
import sh.damon.stackmob.util.EntityHelper;

import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class CreateStackEntity implements ICommand {
    private final Logger LOGGER = LogManager.getLogger("CreateStackEntity");

    @Override
    public void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, boolean isDedicated) {
        dispatcher.register(
            literal("sm").then(literal("create")
                .then(argument("type", RegistryEntryReferenceArgumentType.registryEntry(registryAccess, RegistryKeys.ENTITY_TYPE))
                    .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                        .then(argument("stack_size", integer(2, 2048))
                            .then(argument("coords", Vec3ArgumentType.vec3())
                                .executes(this)
                            )
                    )
                )
            )
        );

        dispatcher.register(
            literal("sm").then(literal("create").then(
                argument("type", RegistryEntryReferenceArgumentType.registryEntry(registryAccess, RegistryKeys.ENTITY_TYPE))
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
        RegistryEntry.Reference<EntityType<?>> entityType = RegistryEntryReferenceArgumentType.getSummonableEntityType(context, "type");
        final ServerCommandSource source = context.getSource();
        final ServerWorld world = source.getWorld();

        Vec3d coords;
        try {
            coords = Vec3ArgumentType.getPosArgument(context, "coords").toAbsolutePos(source);
        } catch (IllegalArgumentException e) {
            // get position of the player that requested the stackmob
            coords = source.getPosition();
        }

        NbtCompound nbt;
        try {
            nbt = NbtCompoundArgumentType.getNbtCompound(context,"nbt");
        }
        catch (IllegalArgumentException e) {
            nbt = new NbtCompound();
        }
        nbt.putString("id", entityType.registryKey().getValue().toString());

        final Vec3d spawnPos = coords; // convert to final to stop lambda from complaining

        LivingEntity entity = EntityHelper.createNewEntity(world, entityType.registryKey().getValue(), spawnPos, nbt);

        if (entity == null)
            throw new SimpleCommandExceptionType(new LiteralMessage("Failed to create entity")).create();

        if (!EntityHelper.spawnEntity(entity))
            throw new SimpleCommandExceptionType(new LiteralMessage("Failed to create entity, UUID duplicate in registry.")).create();

        final StackMob sm = StackMob.getInstance();
        StackEntity stackEntity;
        if (sm.entityManager.isRegistered(entity))
            stackEntity = sm.entityManager.getStackedEntity(entity);
        else
            stackEntity = sm.entityManager.register(entity);

        int size = getInteger(context, "stack_size");
        if (size < 1 || size > stackEntity.getMaxSize())
            throw new SimpleCommandExceptionType(new LiteralMessage("Stack size is large than the maximum stack size or smaller than 1.")).create();

        stackEntity.setSize(size);

        return SINGLE_SUCCESS;
    }
}
