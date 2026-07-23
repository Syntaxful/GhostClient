package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.RenderEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import net.minecraft.client.gui.ScaledResolution;

/**
 * Displays current FPS on screen.
 */
public class FPS extends Module {

    public FPS() {
        super(Category.Render, "FPS", "Show FPS counter.");
    }

    @EventHandler
    public void onRender(RenderEvent.Post event) {
        if (mc.fontRendererObj == null) return;
        ScaledResolution sr = new ScaledResolution(mc);
        String text = "FPS: " + mc.getDebugFPS();
        mc.fontRendererObj.drawStringWithShadow(text, sr.getScaledWidth() - mc.fontRendererObj.getStringWidth(text) - 4, 4, 0xFFFFFFFF);
    }
}
