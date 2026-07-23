package ghostclient.module.modules.movement;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Walk on water.
 */
public class Jesus extends Module {

    public Jesus() {
        super(Category.Movement, "Jesus", "Walk on water.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        if (mc.player.isInWater() && mc.player.motionY < 0) {
            mc.player.motionY = 0.05;
            mc.player.onGround = true;
        }
    }
}
