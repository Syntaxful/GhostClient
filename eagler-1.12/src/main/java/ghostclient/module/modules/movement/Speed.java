package ghostclient.module.modules.movement;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;

/**
 * Increases movement speed.
 */
public class Speed extends Module {

    private final NumberValue multiplier = new NumberValue("Multiplier", "Speed multiplier", 1.5, 1.0, 4.0, 0.1);

    public Speed() {
        super(Category.Movement, "Speed", "Move faster than normal.");
        addSetting(multiplier);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        if (mc.player.onGround && (mc.player.moveForward != 0 || mc.player.moveStrafing != 0)) {
            double m = multiplier.getValue();
            mc.player.motionX *= m;
            mc.player.motionZ *= m;
        }
    }
}
