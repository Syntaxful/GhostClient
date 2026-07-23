package ghostclient.module.modules.render;

import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Removes the hurt camera shake.
 */
public class NoHurtCam extends Module {

    public NoHurtCam() {
        super(Category.Render, "NoHurtCam", "Disables the hurt camera shake.");
    }
}
