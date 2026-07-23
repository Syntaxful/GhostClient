package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.RenderEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Predicts projectile trajectories.
 */
public class Trajectories extends Module {

    public Trajectories() {
        super(Category.Render, "Trajectories", "Show where projectiles will land.");
    }

    @EventHandler
    public void onRender(RenderEvent.Post event) {
        // TODO: draw bow/ender pearl trajectory prediction
    }
}
