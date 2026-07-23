package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.KeyInputEvent;
import ghostclient.event.RenderEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import net.minecraft.client.gui.ScaledResolution;

public class Keystrokes extends Module {

    public Keystrokes() {
        super(Category.Render, "Keystrokes", "Show key presses.");
    }

    @EventHandler
    public void onKey(KeyInputEvent event) {
    }

    @EventHandler
    public void onRender(RenderEvent.Post event) {
        if (mc.fontRendererObj == null || mc.player == null) return;
        ScaledResolution sr = new ScaledResolution(mc);
        String text = "[WASD] [LMB] [RMB] [Space] [Sprint]";
        mc.fontRendererObj.drawStringWithShadow(text, sr.getScaledWidth() / 2 - mc.fontRendererObj.getStringWidth(text) / 2, sr.getScaledHeight() - mc.fontRendererObj.FONT_HEIGHT - 4, 0xFFFFFFFF);
    }
}