package ghostclient.module.modules.combat;

import ghostclient.GhostClient;
import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;

/**
 * Automatically W-taps to reset sprint when hitting an entity.
 */
public class AutoWtap extends Module {

    private int releaseTicks = 0;

    public AutoWtap() {
        super(Category.Combat, "AutoWtap", "Auto W-tap on hit.");
    }

    @Override
    public void onEnable() {
        Module stap = GhostClient.MODULES.getByName("AutoStap");
        if (stap != null && stap.isEnabled()) stap.setEnabled(false);
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
