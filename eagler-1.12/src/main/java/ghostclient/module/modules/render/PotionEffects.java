package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.RenderEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import net.minecraft.potion.PotionEffect;

public class PotionEffects extends Module {

    public PotionEffects() {
        super(Category.Render, "PotionEffects", "Show active potion effects.");
    }

    @EventHandler
    public void onRender(RenderEvent.Post event) {
        if (mc.player == null || mc.fontRendererObj == null) return;
        int y = 4;
        for (PotionEffect effect : mc.player.getActivePotionEffects()) {
            int seconds = effect.getDuration() / 20;
            String duration = String.format("%d:%02d", seconds / 60, seconds % 60);
            String text = effect.getEffectName() + " " + (effect.getAmplifier() + 1) + " " + duration;
            mc.fontRendererObj.drawStringWithShadow(text, 4, y, 0xFFFFFFFF);
            y += mc.fontRendererObj.FONT_HEIGHT + 2;
        }
    }
}