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
            float yaw = mc.player.rotationYaw;
            double forward = mc.player.moveForward;
            double strafe = mc.player.moveStrafing;
            double speed = 0.2873 * multiplier.getValue();
            float yawRad = (float) Math.toRadians(yaw - 90.0f);
            double mx = Math.cos(yawRad) * speed * forward + Math.cos(yawRad + Math.PI / 2.0) * speed * strafe;
            double mz = Math.sin(yawRad) * speed * forward + Math.sin(yawRad + Math.PI / 2.0) * speed * strafe;
            mc.player.motionX = mx;
            mc.player.motionZ = mz;
        }
    }
}
