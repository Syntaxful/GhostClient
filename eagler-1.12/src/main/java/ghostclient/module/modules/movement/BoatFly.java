package ghostclient.module.modules.movement;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Fly while riding a boat.
 */
public class BoatFly extends Module {

    private final NumberValue speed = new NumberValue("Speed", "Forward speed", 1.0, 0.1, 5.0, 0.1);
    private final NumberValue vertical = new NumberValue("Vertical", "Vertical speed", 0.5, 0.1, 2.0, 0.1);

    public BoatFly() {
        super(Category.Movement, "BoatFly", "Fly while in a boat.");
        addSetting(speed);
        addSetting(vertical);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || !(mc.player.getRidingEntity() instanceof net.minecraft.entity.item.EntityBoat)) return;
        net.minecraft.entity.Entity boat = mc.player.getRidingEntity();
        boat.motionY = 0;
        boat.motionX *= speed.getValue();
        boat.motionZ *= speed.getValue();
        if (mc.gameSettings.keyBindJump.isKeyDown()) boat.motionY = vertical.getValue();
        if (mc.gameSettings.keyBindSneak.isKeyDown()) boat.motionY = -vertical.getValue();
    }
}
