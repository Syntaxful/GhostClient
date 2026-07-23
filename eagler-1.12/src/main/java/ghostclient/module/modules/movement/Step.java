package ghostclient.module.modules.movement;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;

/**
 * Automatically steps up blocks.
 */
public class Step extends Module {

    private final NumberValue height = new NumberValue("Height", "Maximum step height", 1.0, 0.5, 3.0, 0.1);

    public Step() {
        super(Category.Movement, "Step", "Automatically step up blocks.");
        addSetting(height);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        mc.player.stepHeight = height.getFloat();
    }

    @Override
    public void onDisable() {
        if (mc.player == null) return;
        mc.player.stepHeight = 0.6F;
    }
}
