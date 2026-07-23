package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Removes blindness and nausea effects.
 */
public class AntiBlind extends Module {

    public AntiBlind() {
        super(Category.Render, "AntiBlind", "Remove blindness and nausea.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        if (mc.player.isPotionActive(net.minecraft.init.MobEffects.BLINDNESS)) {
            mc.player.removeActivePotionEffect(net.minecraft.init.MobEffects.BLINDNESS);
        }
        if (mc.player.isPotionActive(net.minecraft.init.MobEffects.NAUSEA)) {
            mc.player.removeActivePotionEffect(net.minecraft.init.MobEffects.NAUSEA);
        }
    }
}
