package ghostclient.module.modules.movement;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;

/**
 * Better elytra flight with adjustable speed (1-100 bps).
 */
public class ElytraFly extends Module {

    private final NumberValue speed = new NumberValue("Speed", "Forward speed (blocks per second)", 20.0, 1.0, 100.0, 1.0);
    private final NumberValue vertical = new NumberValue("Vertical", "Vertical speed (blocks per second)", 10.0, 1.0, 50.0, 1.0);

    public ElytraFly() {
        super(Category.Movement, "ElytraFly", "Elytra flight control.");
        addSetting(speed);
        addSetting(vertical);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || !mc.player.isElytraFlying()) return;

        // Convert bps to per-tick velocity (20 ticks/sec)
        double bps = Math.min(speed.getValue(), 50.0) / 20.0;
        double yawRad = Math.toRadians(mc.player.rotationYaw);
        mc.player.motionX = -Math.sin(yawRad) * bps;
        mc.player.motionZ =  Math.cos(yawRad) * bps;

        double vBps = Math.min(vertical.getValue(), 20.0) / 20.0;
        if (mc.gameSettings.keyBindJump.isKeyDown())  mc.player.motionY =  vBps;
        else if (mc.gameSettings.keyBindSneak.isKeyDown()) mc.player.motionY = -vBps;
        else mc.player.motionY = Math.max(-0.08, mc.player.motionY - 0.03); // gentle gravity
    }
}
