package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.RenderEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Disables fog rendering.
 */
public class NoFog extends Module {

    public NoFog() {
        super(Category.Render, "NoFog", "Disable fog rendering.");
    }

    @EventHandler
    public void onRender(RenderEvent.Post event) {
        if (mc.entityRenderer != null) {
            mc.entityRenderer.fogStandard = false;
        }
    }

    public boolean shouldDisableFog() {
        return isEnabled();
    }
}
