package ghostclient.module.modules.player;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Use items faster.
 */
public class FastUse extends Module {

    public FastUse() {
        super(Category.Player, "FastUse", "Use items faster.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || !mc.player.isHandActive()) return;
        // removed reflection call to activeItemStackUseCount
    }
}
