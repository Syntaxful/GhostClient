package ghostclient.module.modules.world;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Breaks blocks faster by clearing the click delay.
 */
public class FastBreak extends Module {

    public FastBreak() {
        super(Category.World, "FastBreak", "Break blocks faster.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        // removed due to private API
    }
}
