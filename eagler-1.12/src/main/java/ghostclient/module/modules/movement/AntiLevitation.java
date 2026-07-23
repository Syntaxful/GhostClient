package ghostclient.module.modules.movement;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Cancels levitation effects.
 */
public class AntiLevitation extends Module {

    public AntiLevitation() {
        super(Category.Movement, "AntiLevitation", "Cancel levitation effects.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        if (mc.player.isPotionActive(net.minecraft.init.MobEffects.LEVITATION)) {
            mc.player.motionY = -0.05;
        }
    }
}
