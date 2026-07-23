package ghostclient.module.modules.misc;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Automatically respawns the player after death.
 */
public class AutoRespawn extends Module {

    public AutoRespawn() {
        super(Category.Misc, "AutoRespawn", "Auto respawn after death.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        if (!mc.player.isEntityAlive()) {
            mc.player.respawnPlayer();
        }
    }
}
