package ghostclient.module.modules.movement;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;

/**
 * Climb ladders and vines faster. Uses key-down detection for direction.
 */
public class FastClimb extends Module {

    private final NumberValue speed = new NumberValue("Speed", "Climb speed (blocks per tick)", 0.3, 0.05, 1.0, 0.05);

    public FastClimb() {
        super(Category.Movement, "FastClimb", "Climb ladders and vines faster.");
        addSetting(speed);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        if (!mc.player.isOnLadder()) return;

        if (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.player.motionY = speed.getValue();
        } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            mc.player.motionY = -speed.getValue();
        }
    }
}
