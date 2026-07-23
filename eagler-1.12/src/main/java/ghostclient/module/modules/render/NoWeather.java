package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Disables rain and snow rendering.
 */
public class NoWeather extends Module {

    public NoWeather() {
        super(Category.Render, "NoWeather", "Disables rain and snow.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.world != null) {
            mc.world.setRainStrength(0);
            mc.world.setThunderStrength(0);
        }
    }

    public boolean shouldDisableWeather() {
        return isEnabled();
    }
}
