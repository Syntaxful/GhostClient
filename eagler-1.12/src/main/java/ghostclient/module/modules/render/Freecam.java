package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;

/**
 * Freecam mode.
 */
public class Freecam extends Module {

    private final NumberValue speed = new NumberValue("Speed", "Freecam speed", 1.0, 0.1, 5.0, 0.1);
    private double startX, startY, startZ;

    public Freecam() {
        super(Category.Render, "Freecam", "Spectator-like free camera.");
        addSetting(speed);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        mc.player.capabilities.isFlying = true;
        mc.player.capabilities.setFlySpeed(speed.getFloat());
    }

    @Override
    public void onEnable() {
        if (mc.player == null) return;
        startX = mc.player.posX;
        startY = mc.player.posY;
        startZ = mc.player.posZ;
    }

    @Override
    public void onDisable() {
        if (mc.player == null) return;
        mc.player.capabilities.isFlying = false;
    }
}
