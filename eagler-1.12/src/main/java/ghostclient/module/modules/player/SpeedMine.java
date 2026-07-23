package ghostclient.module.modules.player;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Mines blocks faster.
 */
public class SpeedMine extends Module {

    public SpeedMine() {
        super(Category.Player, "SpeedMine", "Mine blocks faster.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        // removed due to private API
    }
}
