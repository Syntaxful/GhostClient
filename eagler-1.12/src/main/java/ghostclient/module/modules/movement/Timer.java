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

    private final NumberValue speed = new NumberValue("Speed", "Timer speed", 1.5, 0.1, 5.0, 0.1);

    public Timer() {
        super(Category.Movement, "Timer", "Speed up the game timer.");
        addSetting(speed);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        // removed due to private API
    }

    @Override
    public void onDisable() {
        // removed due to private API
    }
}
