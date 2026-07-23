package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.RenderEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

public class NoFog extends Module {

    public NoFog() {
        super(Category.Render, "NoFog", "Disable fog rendering.");
    }

    @EventHandler
    public void onRender(RenderEvent.Post event) {
        if (mc.entityRenderer != null) {
            mc.entityRenderer.fogStandard = false;
            // TODO: Hook fog state more directly if needed.
        }
    }
}