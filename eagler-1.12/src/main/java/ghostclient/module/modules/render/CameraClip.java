package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Clips the third-person camera through blocks.
 */
public class CameraClip extends Module {

    public CameraClip() {
        super(Category.Render, "CameraClip", "Third person camera passes through blocks.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        // The actual clipping is applied via a patch in EntityRenderer#orientCamera.
        // This module signals the patch to ignore block distance when enabled.
    }
}
