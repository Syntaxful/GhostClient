package ghostclient.module.modules.misc;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Custom window title.
 */
public class WindowTitle extends Module {

    public WindowTitle() {
        super(Category.Misc, "WindowTitle", "Custom window title.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        // TODO: set Display.setTitle("GhostClient | " + VERSION)
    }
}
