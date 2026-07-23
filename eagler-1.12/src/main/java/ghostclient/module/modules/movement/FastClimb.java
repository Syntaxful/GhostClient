package ghostclient.module.modules.movement;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;

/**
 * Climb ladders faster.
 */
public class FastClimb extends Module {

    private final NumberValue speed = new NumberValue("Speed", "Climb speed", 0.3, 0.1, 1.0, 0.05);

    public FastClimb() {
        super(Category.Movement, "FastClimb", "Climb ladders and vines faster.");
        addSetting(speed);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        if (mc.player.isOnLadder() && mc.player.moveForward > 0) {
            mc.player.motionY = speed.getValue();
        }
    }
}
