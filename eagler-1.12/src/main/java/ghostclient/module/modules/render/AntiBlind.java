package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import net.minecraft.init.MobEffects;

public class AntiBlind extends Module {

    public AntiBlind() {
        super(Category.Render, "AntiBlind", "Remove blindness and nausea effects.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        if (mc.player.getActivePotionEffect(MobEffects.BLINDNESS) != null) mc.player.removePotionEffect(MobEffects.BLINDNESS);
        if (mc.player.getActivePotionEffect(MobEffects.NAUSEA) != null) mc.player.removePotionEffect(MobEffects.NAUSEA);
    }
}