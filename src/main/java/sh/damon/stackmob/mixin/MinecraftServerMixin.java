package sh.damon.stackmob.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sh.damon.stackmob.StackMob;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(at = @At("RETURN"), method = "prepareStartRegion")
    public void onServerStart(WorldGenerationProgressListener worldGenerationProgressListener, CallbackInfo ci) {
        StackMob.log.info("Server finished preparing start region.");

        MinecraftServer server = (MinecraftServer) (Object) this;

        StackMob sm = StackMob.getInstance();
        sm.entityManager.registerAll(server);
    }
}
