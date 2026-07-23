package ghostclient.module.modules.render;

import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;

/**
 * Sol Client style low fire overlay.
 */
public class LowFire extends Module {

    public final NumberValue height = new NumberValue("Height", "Fire overlay height", 0.0, 0.0, 1.0, 0.05);

    public LowFire() {
        super(Category.Render, "LowFire", "Lower the fire overlay.");
        addSetting(height);
    }
}
