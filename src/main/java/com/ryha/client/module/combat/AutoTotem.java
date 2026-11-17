package com.ryha.client.module.combat;

import com.ryha.client.RyHaClient;
import com.ryha.client.event.Subscribe;
import com.ryha.client.event.events.TickEvent;
import com.ryha.client.module.Category;
import com.ryha.client.module.Module;
import com.ryha.client.module.setting.SliderSetting;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.world.InteractionHand;

public class AutoTotem extends Module {

    private final SliderSetting  healthThreshold = registerSetting(
        new SliderSetting("Health", "Re-equip when health below", 10, 1, 20, 1));

    public AutoTotem() {
        super("AutoTotem", "Automatically keeps a totem of undying in off-hand", Category.COMBAT);
    }

    @Override protected void onEnable() { RyHaClient.getInstance().getEventBus().registerObject(this); }

    @Subscribe
    public void onTick(TickEvent event) {
        LocalPlayer p = mc.player;
        if (p == null || mc.gameMode == null) return;

        // Off-hand already has a totem and health is fine → skip
        if (p.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING)
            && p.getHealth() > healthThreshold.getValue()) return;

        // Find totem in inventory (slots 9-44, slot 45 = off-hand)
        var inv = p.getInventory();
        for (int i = 0; i < inv.size(); i++) {
            if (inv.getStack(i).isOf(Items.TOTEM_OF_UNDYING)) {
                // Swap with off-hand via inventory click packet
                // Raw slot index for off-hand is 45 in container 0
                mc.gameMode.clickSlot(p.playerScreenHandler.syncId,
                    i < 9 ? i + 36 : i,          // hotbar vs inventory mapping
                    0, SlotActionType.PICKUP, p);
                mc.gameMode.clickSlot(p.playerScreenHandler.syncId,
                    45, 0, SlotActionType.PICKUP, p);
                mc.gameMode.clickSlot(p.playerScreenHandler.syncId,
                    i < 9 ? i + 36 : i,
                    0, SlotActionType.PICKUP, p);
                return;
            }
        }
    }
}
