package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import net.lax1dude.eaglercraft.Display;

/**
 * Reduces CPU usage when the game is not focused.
 */
public class UnfocusedCPU extends Module {

    public UnfocusedCPU() {
        super(Category.Render, "UnfocusedCPU", "Limit FPS when the window is not focused.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.gameSettings == null) return;
        if (!Display.isActive()) {
            mc.gameSettings.limitFramerate = 30;
        } else {
            mc.gameSettings.limitFramerate = 120;
        }
    }

    @Override
    public void onDisable() {
        if (mc.gameSettings == null) return;
        mc.gameSettings.limitFramerate = 120;
    }
}
