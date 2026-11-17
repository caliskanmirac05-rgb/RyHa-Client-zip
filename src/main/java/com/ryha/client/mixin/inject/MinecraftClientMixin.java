package com.ryha.client.mixin.inject;

import com.ryha.client.RyHaClient;
import com.ryha.client.module.player.InventoryMove;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public abstract class MinecraftClientMixin {

    /**
     * InventoryMove: return false (not in-screen) so movement keys still work.
     */
    @Inject(method = "isScreenOpen", at = @At("RETURN"), cancellable = true)
    private void allowMovementInGUI(CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue()) return;
        if (RyHaClient.getInstance() == null) return;
        if (RyHaClient.getInstance().getModuleManager().isEnabled(InventoryMove.class)) {
            cir.setReturnValue(false);
        }
    }
}
