package ghostclient.module.modules.world;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Removes placement delay.
 */
public class FastPlace extends Module {

    public FastPlace() {
        super(Category.World, "FastPlace", "Place blocks faster.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        // removed reflection call to rightClickDelayTimer
    }
}
