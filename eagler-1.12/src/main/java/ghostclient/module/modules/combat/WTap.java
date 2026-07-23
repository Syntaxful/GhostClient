package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Automatically W-taps to reset sprint when hitting an entity.
 */
public class WTap extends Module {

    private int releaseTicks = 0;

    public WTap() {
        super(Category.Combat, "WTap", "Auto W-tap on hit.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        if (releaseTicks > 0) {
            mc.gameSettings.keyBindForward.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);
            releaseTicks--;
            if (releaseTicks == 0) {
                mc.gameSettings.keyBindForward.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
            }
        }
    }

    public void onAttack() {
        if (isEnabled() && mc.player != null && mc.player.isSprinting()) {
            releaseTicks = 2;
        }
    }
}
