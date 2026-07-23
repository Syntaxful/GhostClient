package ghostclient.module.modules.combat;

import ghostclient.GhostClient;
import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;

/**
 * Automatically W-taps to reset sprint when hitting an entity.
 * Mutually exclusive with STap.
 */
public class AutoWtap extends Module {

    private final NumberValue release = new NumberValue("Release Ticks", "How long to stop holding W", 2, 1, 8, 1);
    private final NumberValue hold    = new NumberValue("Hold Ticks",    "How long to hold W after release", 2, 1, 8, 1);
    private int releaseTicks = 0;
    private int holdTicks = 0;

    public AutoWtap() {
        super(Category.Combat, "WTap", "Auto W-tap on hit.");
        addSetting(release);
        addSetting(hold);
    }

    @Override
    public void onEnable() {
        Module stap = GhostClient.MODULES.getByName("STap");
        if (stap != null && stap.isEnabled()) stap.setEnabled(false);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        if (releaseTicks > 0) {
            mc.gameSettings.keyBindForward.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);
            releaseTicks--;
            if (releaseTicks == 0) holdTicks = hold.getInt();
            return;
        }
        if (holdTicks > 0) {
            mc.gameSettings.keyBindForward.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
            holdTicks--;
        }
    }

    public void onAttack() {
        if (isEnabled() && mc.player != null && mc.player.isSprinting() && releaseTicks == 0 && holdTicks == 0) {
            releaseTicks = release.getInt();
        }
    }
}
