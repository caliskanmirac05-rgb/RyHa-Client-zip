package com.ryha.client.module.player;

import com.ryha.client.RyHaClient;
import com.ryha.client.event.Subscribe;
import com.ryha.client.event.events.TickEvent;
import com.ryha.client.module.Category;
import com.ryha.client.module.Module;
import net.minecraft.world.level.block.BlockState;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;

public class AutoTool extends Module {

    public AutoTool() {
        super("AutoTool", "Automatically switches to best tool for block", Category.PLAYER);
    }

    @Override protected void onEnable()  { RyHaClient.getInstance().getEventBus().registerObject(this); }

    @Subscribe
    public void onTick(TickEvent event) {
        LocalPlayer p = mc.player;
        if (p == null || mc.level == null || mc.crosshairTarget == null) return;
        if (!(mc.crosshairTarget instanceof net.minecraft.util.hit.BlockHitResult bhr)) return;

        BlockPos pos = bhr.blockPosition();
        BlockState state = mc.level.getBlockState(pos);
        if (state.isAir()) return;

        float bestSpeed = -1f;
        int bestSlot = p.getInventory().selectedSlot;

        for (int i = 0; i < 9; i++) {
            ItemStack stack = p.getInventory().getItem(i);
            float speed = stack.getDestroySpeed(state);
            if (speed > bestSpeed) {
                bestSpeed = speed;
                bestSlot  = i;
            }
        }
        p.getInventory().selectedSlot = bestSlot;
    }
}
