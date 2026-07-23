package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.RenderEvent;
import ghostclient.GhostClient;
import ghostclient.module.Category;
import ghostclient.module.Module;
import net.minecraft.client.gui.ScaledResolution;

public class Speedometer extends Module {

    public Speedometer() {
        super(Category.Render, "Speedometer", "Show player speed in blocks per second.");
    }

    @EventHandler
    public void onRender(RenderEvent.Post event) {
        if (mc.player == null || mc.fontRendererObj == null) return;
        ScaledResolution sr = new ScaledResolution(mc);
        String text = String.format("Speed: %.1f b/s", Math.hypot(mc.player.motionX, mc.player.motionZ) * 20.0D);
        int y = 4;
        if (GhostClient.MODULES.getByName("Coordinates") != null && GhostClient.MODULES.getByName("Coordinates").isEnabled()) {
            y += mc.fontRendererObj.FONT_HEIGHT + 2;
        }
        mc.fontRendererObj.drawStringWithShadow(text, 4, sr.getScaledHeight() - mc.fontRendererObj.FONT_HEIGHT - y, 0xFFFFFFFF);
    }
}