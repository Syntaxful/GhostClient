package ghostclient.module.modules.misc;

import ghostclient.GhostClient;
import ghostclient.event.EventHandler;
import ghostclient.event.KeyInputEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Disables all modules instantly.
 */
public class Panic extends Module {

    public Panic() {
        super(Category.Misc, "Panic", "Disable all modules.");
        setKeybind(25); // P
    }

    @EventHandler
    public void onKey(KeyInputEvent event) {
        if (event.getKey() == getKeybind() && event.isPressed()) {
            for (Module module : GhostClient.MODULES.getAll()) {
                if (module != this) module.setEnabled(false);
            }
        }
    }
}
