package com.ryha.client.module.combat;

import com.ryha.client.RyHaClient;
import com.ryha.client.event.Subscribe;
import com.ryha.client.event.events.AttackEvent;
import com.ryha.client.module.Category;
import com.ryha.client.module.Module;
import com.ryha.client.module.setting.ModeSetting;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class Criticals extends Module {

    private final ModeSetting mode = registerSetting(
        new ModeSetting("Mode", "Critical mode", "Packet", "Packet", "Jump", "MiniJump")
    );

    public Criticals() {
        super("Criticals", "Always lands critical hits on enemies", Category.COMBAT);
    }

    @Override protected void onEnable()  { RyHaClient.getInstance().getEventBus().registerObject(this); }

    @Subscribe
    public void onAttack(AttackEvent event) {
        if (mc.player == null || mc.player.isOnGround()) {
            return;
        }

        switch (mode.getValue()) {
            case "Packet" -> {
                // Send micro-packets to trigger critical state server-side
                var net = mc.getNetworkHandler();
                if (net == null) return;
                double x = mc.player.getX(), y = mc.player.getY(), z = mc.player.getZ();
                net.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y + 0.0625, z, false, mc.player.horizontalCollision));
                net.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y,          z, false, mc.player.horizontalCollision));
                net.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y + 1.1e-7, z, false, mc.player.horizontalCollision));
                net.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y,          z, false, mc.player.horizontalCollision));
            }
            case "Jump" -> mc.player.jump();
            case "MiniJump" -> mc.player.addVelocity(0, 0.08, 0);
        }
    }
}
