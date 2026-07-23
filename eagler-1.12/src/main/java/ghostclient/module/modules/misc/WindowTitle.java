package ghostclient.module.modules.misc;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import net.lax1dude.eaglercraft.Display;

/**
 * Custom window title.
 */
public class WindowTitle extends Module {

    public WindowTitle() {
        super(Category.Misc, "WindowTitle", "Custom window title.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        try {
            Display.setTitle("GhostClient | Eagler 1.12");
        } catch (Exception ignored) {
            // Eagler may not expose Display directly; title is handled by the web wrapper.
        }
    }
}
