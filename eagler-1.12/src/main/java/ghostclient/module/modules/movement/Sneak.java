package ghostclient.module.modules.movement;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Force sneak.
 */
public class Sneak extends Module {

    public Sneak() {
        super(Category.Movement, "Sneak", "Always sneak.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        mc.player.setSneaking(true);
    }

    @Override
    public void onDisable() {
        if (mc.player == null) return;
        mc.player.setSneaking(false);
    }
}
