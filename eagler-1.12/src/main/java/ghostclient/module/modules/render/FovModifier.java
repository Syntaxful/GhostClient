package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;

/**
 * Sol Client style static FOV modifier.
 */
public class FovModifier extends Module {

    private final NumberValue fov = new NumberValue("FOV", "Field of view", 90, 30, 180, 1);

    public FovModifier() {
        super(Category.Render, "FovModifier", "Static FOV modifier.");
        addSetting(fov);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        // The patched EntityRenderer#getFOVModifier reads this value directly,
        // so we don't need to overwrite the global setting and fight with Zoom.
    }

    public float getFov() {
        return fov.getFloat();
    }
}
