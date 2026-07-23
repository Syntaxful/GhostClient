package ghostclient.module.modules.movement;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Completely removes the levitation effect.
 */
public class AntiLevitation extends Module {

    public AntiLevitation() {
        super(Category.Movement, "AntiLevitation", "Fully removes levitation effects.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        if (mc.player.isPotionActive(net.minecraft.init.MobEffects.LEVITATION)) {
            mc.player.removeActivePotionEffect(net.minecraft.init.MobEffects.LEVITATION);
            mc.player.motionY = 0.0;
        }
    }
}
