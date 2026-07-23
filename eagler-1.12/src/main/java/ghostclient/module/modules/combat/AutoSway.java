package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;

/**
 * Automatically strafes side to side while in combat.
 */
public class AutoSway extends Module {

    private final NumberValue speed = new NumberValue("Speed", "Strafe speed", 0.5, 0.1, 1.0, 0.05);
    private int ticks = 0;
    private int dir = 1;

    public AutoSway() {
        super(Category.Combat, "AutoSway", "Auto move side to side.");
        addSetting(speed);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        ticks++;
        if (ticks % 8 == 0) dir = -dir;
        mc.player.moveStrafing = dir * speed.getFloat();
        mc.player.setSprinting(true);
    }

    @Override
    public void onDisable() {
        if (mc.player != null) mc.player.moveStrafing = 0.0f;
    }
}
