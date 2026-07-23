package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.RenderEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Stacks boss bars on top of each other.
 */
public class BossStack extends Module {

    public BossStack() {
        super(Category.Render, "BossStack", "Stack boss bars instead of overlapping.");
    }

    @EventHandler
    public void onRender(RenderEvent.Post event) {
        // TODO: patch boss bar rendering to stack bars
    }
}
