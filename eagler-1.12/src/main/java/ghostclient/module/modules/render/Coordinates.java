package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.RenderEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Displays player coordinates on screen.
 */
public class Coordinates extends Module {

    public Coordinates() {
        super(Category.Render, "Coordinates", "Show X/Y/Z coordinates.");
    }

    @EventHandler
    public void onRender(RenderEvent.Post event) {
        if (mc.player == null || mc.fontRendererObj == null) return;
        String text = String.format("X: %.0f Y: %.0f Z: %.0f", mc.player.posX, mc.player.posY, mc.player.posZ);
        mc.fontRendererObj.drawStringWithShadow(text, 4, 4, 0xFFFFFFFF);
    }
}
