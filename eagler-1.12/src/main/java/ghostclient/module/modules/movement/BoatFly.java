package ghostclient.module.modules.movement;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Fly while riding a boat.
 */
public class BoatFly extends Module {

    public BoatFly() {
        super(Category.Movement, "BoatFly", "Fly while in a boat.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || !(mc.player.getRidingEntity() instanceof net.minecraft.entity.item.EntityBoat)) return;
        mc.player.getRidingEntity().motionY = 0;
        if (mc.gameSettings.keyBindJump.isKeyDown()) mc.player.getRidingEntity().motionY = 0.5;
        if (mc.gameSettings.keyBindSneak.isKeyDown()) mc.player.getRidingEntity().motionY = -0.5;
    }
}
