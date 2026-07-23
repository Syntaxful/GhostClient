package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.RenderEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Sol Client style toggle sprint indicator.
 */
public class ToggleSprint extends Module {

    public ToggleSprint() {
        super(Category.Render, "ToggleSprint", "Show sprinting status on screen.");
    }

    @EventHandler
    public void onRender(RenderEvent.Post event) {
        if (mc.fontRendererObj == null || mc.player == null) return;
        String text = "Sprint: " + (mc.player.isSprinting() ? "ON" : "OFF");
        mc.fontRendererObj.drawStringWithShadow(text, 4, 16, 0xFFFFFFFF);
    }
}
