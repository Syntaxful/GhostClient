package ghostclient.module.modules.movement;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Better elytra flight.
 */
public class ElytraFly extends Module {

    public ElytraFly() {
        super(Category.Movement, "ElytraFly", "Elytra flight control.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || !mc.player.isElytraFlying()) return;
        mc.player.motionY = 0;
        if (mc.gameSettings.keyBindJump.isKeyDown()) mc.player.motionY = 0.5;
        if (mc.gameSettings.keyBindSneak.isKeyDown()) mc.player.motionY = -0.5;
    }
}
