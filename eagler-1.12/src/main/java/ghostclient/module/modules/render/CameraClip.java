package ghostclient.module.modules.render;

import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Clips the third-person camera through blocks.
 */
public class CameraClip extends Module {

    public CameraClip() {
        super(Category.Render, "CameraClip", "Third person camera passes through blocks.");
    }
}
