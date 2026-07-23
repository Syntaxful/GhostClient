package ghostclient.module.modules.movement;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Removes item slowdown.
 */
public class NoSlow extends Module {

    public NoSlow() {
        super(Category.Movement, "NoSlow", "Move at normal speed while using items.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        if (mc.player.isHandActive()) {
            mc.player.moveStrafing *= 1.0;
            mc.player.moveForward *= 1.0;
        }
    }
}
