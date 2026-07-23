package ghostclient.module.modules.movement;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Automatically sprints when moving forward.
 */
public class Sprint extends Module {

    public Sprint() {
        super(Category.Movement, "Sprint", "Automatically sprint when moving forward.");
    }

    @EventHandler
    @Override
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        if (mc.player.moveForward > 0 && !mc.player.isSprinting() && !mc.player.isSneaking() && mc.player.getFoodStats().getFoodLevel() > 6) {
            mc.player.setSprinting(true);
        }
    }
}
