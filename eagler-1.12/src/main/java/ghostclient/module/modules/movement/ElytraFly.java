package ghostclient.module.modules.movement;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Better elytra flight.
 */
public class ElytraFly extends Module {

    private final NumberValue speed = new NumberValue("Speed", "Forward speed", 1.0, 0.1, 5.0, 0.1);
    private final NumberValue vertical = new NumberValue("Vertical", "Vertical speed", 0.5, 0.1, 2.0, 0.1);

    public ElytraFly() {
        super(Category.Movement, "ElytraFly", "Elytra flight control.");
        addSetting(speed);
        addSetting(vertical);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || !mc.player.isElytraFlying()) return;
        mc.player.motionY = 0;
        mc.player.motionX *= speed.getValue();
        mc.player.motionZ *= speed.getValue();
        if (mc.gameSettings.keyBindJump.isKeyDown()) mc.player.motionY = vertical.getValue();
        if (mc.gameSettings.keyBindSneak.isKeyDown()) mc.player.motionY = -vertical.getValue();
    }
}
