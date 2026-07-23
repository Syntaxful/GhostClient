package ghostclient.module.modules.misc;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Spawns a fake player for testing.
 */
public class FakePlayer extends Module {

    public FakePlayer() {
        super(Category.Misc, "FakePlayer", "Spawns a fake player entity.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        // TODO: spawn a fake player at the player's position
    }
}
