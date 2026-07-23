package ghostclient.module.modules.misc;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Disables view bobbing and hand/item animations.
 */
public class NoAnimations extends Module {

    public NoAnimations() {
        super(Category.Misc, "NoAnimations", "Disable view bobbing and hand animations.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        mc.gameSettings.viewBobbing = false;
    }

    @Override
    public void onDisable() {
        if (mc.gameSettings != null) mc.gameSettings.viewBobbing = true;
    }
}
