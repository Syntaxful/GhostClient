package ghostclient.module.modules.render;

import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Disables rain and snow rendering.
 */
public class NoWeather extends Module {

    public NoWeather() {
        super(Category.Render, "NoWeather", "Disables rain and snow.");
    }

    @Override
    public void onEnable() {
        if (mc.world != null) mc.world.setRainStrength(0);
    }
}
