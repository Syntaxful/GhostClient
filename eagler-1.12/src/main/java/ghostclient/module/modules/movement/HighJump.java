package ghostclient.module.modules.movement;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;

/**
 * Jump higher.
 */
public class HighJump extends Module {

    private final NumberValue height = new NumberValue("Height", "Jump height multiplier", 1.5, 1.0, 3.0, 0.1);

    public HighJump() {
        super(Category.Movement, "HighJump", "Jump higher than normal.");
        addSetting(height);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        if (mc.player.onGround && mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.player.motionY = 0.42 * height.getValue();
        }
    }
}
