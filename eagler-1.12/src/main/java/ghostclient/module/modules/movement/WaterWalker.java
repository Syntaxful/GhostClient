package ghostclient.module.modules.movement;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.ModeValue;

/**
 * Walk on water.
 */
public class WaterWalker extends Module {

    private final ModeValue mode = new ModeValue("Mode", "Water walking style", "Solid", "Solid", "Bounce");

    public WaterWalker() {
        super(Category.Movement, "WaterWalker", "Walk on water.");
        addSetting(mode);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        if (mc.player.isInWater() && mc.player.motionY < 0) {
            if ("Solid".equals(mode.getValue())) {
                mc.player.motionY = 0.0;
                mc.player.onGround = true;
            } else {
                mc.player.motionY = 0.05;
                mc.player.onGround = true;
            }
        }
    }
}
