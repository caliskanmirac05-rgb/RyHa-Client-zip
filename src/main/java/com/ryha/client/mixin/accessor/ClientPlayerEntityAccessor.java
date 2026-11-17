package com.ryha.client.mixin.accessor;

import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LocalPlayer.class)
public interface ClientPlayerEntityAccessor {
    @Accessor("ticksLeftToDoubleTapSprint")
    int getSprintTicks();
    @Accessor("ticksLeftToDoubleTapSprint")
    void setSprintTicks(int ticks);
}
