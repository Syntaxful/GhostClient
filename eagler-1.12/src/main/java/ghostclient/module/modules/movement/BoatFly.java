package ghostclient.module.modules.movement;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;
import net.minecraft.entity.item.EntityBoat;

/**
 * Fly while riding a boat (1-50 bps, adjustable).
 */
public class BoatFly extends Module {

    private final NumberValue speed = new NumberValue("Speed", "Horizontal speed (blocks per second)", 5.0, 1.0, 50.0, 0.5);
    private final NumberValue vertical = new NumberValue("Vertical", "Vertical speed (blocks per second)", 5.0, 1.0, 20.0, 0.5);

    public BoatFly() {
        super(Category.Movement, "BoatFly", "Fly while in a boat.");
        addSetting(speed);
        addSetting(vertical);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || !(mc.player.getRidingEntity() instanceof EntityBoat)) return;
        net.minecraft.entity.Entity boat = mc.player.getRidingEntity();

        double bps = speed.getValue() / 20.0;
        double yawRad = Math.toRadians(mc.player.rotationYaw);
        boat.motionX = -Math.sin(yawRad) * bps;
        boat.motionZ =  Math.cos(yawRad) * bps;
        boat.motionY = 0;

        double vBps = vertical.getValue() / 20.0;
        if (mc.gameSettings.keyBindJump.isKeyDown())  boat.motionY =  vBps;
        if (mc.gameSettings.keyBindSneak.isKeyDown()) boat.motionY = -vBps;
    }
}
