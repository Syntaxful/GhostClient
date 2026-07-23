package ghostclient.module.modules.movement;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;

/**
 * Speeds up game time.
 */
public class Timer extends Module {

    private final NumberValue speed = new NumberValue("Speed", "Timer speed", 1.5, 0.1, 10.0, 0.1);

    public Timer() {
        super(Category.Movement, "Timer", "Speed up the game timer.");
        addSetting(speed);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        // Timer is applied via a patch in Minecraft#runTick reading this value.
    }

    public float getSpeed() {
        return isEnabled() ? speed.getFloat() : 1.0f;
    }

    @Override
    public void onDisable() {
        // Timer reset is handled by the patch reading getSpeed() returning 1.0f.
    }
}
