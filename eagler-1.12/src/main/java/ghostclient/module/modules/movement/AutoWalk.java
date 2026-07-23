package ghostclient.module.modules.movement;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Automatically holds forward.
 */
public class AutoWalk extends Module {

    public AutoWalk() {
        super(Category.Movement, "AutoWalk", "Hold forward automatically.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        mc.gameSettings.keyBindForward.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
    }

    @Override
    public void onDisable() {
        if (mc.player == null) return;
        mc.gameSettings.keyBindForward.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);
    }
}
