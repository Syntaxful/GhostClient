package ghostclient.module.modules.movement;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Jump while in the air.
 */
public class AirJump extends Module {

    public AirJump() {
        super(Category.Movement, "AirJump", "Jump while in the air.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        if (mc.gameSettings.keyBindJump.isKeyDown() && !mc.player.onGround) {
            mc.player.motionY = 0.42;
            mc.player.onGround = true;
        }
    }
}
