package gr1mly4memes.slime.mixins.server.dedicated;

import net.minecraft.server.dedicated.DedicatedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DedicatedServer.class)
public class MixinDedicatedServer {

    @Inject(method = "showGui", at = @At("HEAD"), cancellable = true)
    private void slime$disableGui(CallbackInfo ci) {
      ci.cancel();
    }
}
