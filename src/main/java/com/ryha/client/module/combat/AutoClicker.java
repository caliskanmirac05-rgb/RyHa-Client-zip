package com.ryha.client.module.combat;

import com.ryha.client.RyHaClient;
import com.ryha.client.event.Subscribe;
import com.ryha.client.event.events.TickEvent;
import com.ryha.client.module.Category;
import com.ryha.client.module.Module;
import com.ryha.client.module.setting.BooleanSetting;
import com.ryha.client.module.setting.SliderSetting;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.world.InteractionHand;

public class AutoClicker extends Module {

    private final SliderSetting  minCps  = registerSetting(new SliderSetting("MinCPS", "Min clicks per second", 8,  1, 20, 1));
    private final SliderSetting  maxCps  = registerSetting(new SliderSetting("MaxCPS", "Max clicks per second", 14, 1, 20, 1));
    private final BooleanSetting onlyHit = registerSetting(new BooleanSetting("OnlyOnEntity", "Only click when hovering entity", true));
    private final BooleanSetting rightClick = registerSetting(new BooleanSetting("RightClick", "Also right-click", false));

    private long nextClick = 0;

    public AutoClicker() {
        super("AutoClicker", "Automatically clicks at a configured CPS", Category.COMBAT);
    }

    @Override protected void onEnable()  { RyHaClient.getInstance().getEventBus().registerObject(this); }

    @Subscribe
    public void onTick(TickEvent event) {
        if (mc.player == null) return;
        if (!mc.mouse.wasLeftButtonClicked()) return; // LMB must be held

        long now = System.currentTimeMillis();
        if (now < nextClick) return;

        // Randomize within range to humanize
        double cps   = minCps.getValue() + Math.random() * (maxCps.getValue() - minCps.getValue());
        nextClick    = now + (long) (1000.0 / cps);

        if (onlyHit.isEnabled() && mc.targetedEntity == null) return;

        if (mc.targetedEntity != null) {
            mc.gameMode.attackEntity(mc.player, mc.targetedEntity);
            mc.player.swingHand(InteractionHand.MAIN_HAND);
        }
    }
}
