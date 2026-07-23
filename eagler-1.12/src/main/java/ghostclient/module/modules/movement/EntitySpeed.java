package ghostclient.module.modules.movement;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;

/**
 * Speed up ridden entities (1-50 bps, adjustable).
 * Sets a constant velocity so speed stays controlled.
 */
public class EntitySpeed extends Module {

    private final NumberValue speed = new NumberValue("Speed", "Entity speed (blocks per second)", 5.0, 1.0, 50.0, 0.5);

    public EntitySpeed() {
        super(Category.Movement, "EntitySpeed", "Speed up mounts and boats.");
        addSetting(speed);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.player.getRidingEntity() == null) return;
        net.minecraft.entity.Entity e = mc.player.getRidingEntity();

        // Set constant forward velocity — never multiply (that causes exponential growth)
        double bps = speed.getValue() / 20.0;
        double yawRad = Math.toRadians(mc.player.rotationYaw);
        e.motionX = -Math.sin(yawRad) * bps;
        e.motionZ =  Math.cos(yawRad) * bps;
    }
}
