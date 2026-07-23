package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;
import net.minecraft.util.EnumHand;

/**
 * Automatically clicks when holding attack.
 */
public class AutoClicker extends Module {

    private final NumberValue cps = new NumberValue("CPS", "Clicks per second", 12, 1, 30, 1);
    private int ticksDown = 0;

    public AutoClicker() {
        super(Category.Combat, "AutoClicker", "Hold attack to auto-click.");
        addSetting(cps);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.gameSettings.keyBindAttack.isKeyDown()) return;
        int interval = 20 / cps.getInt();
        if (interval < 1) interval = 1;
        ticksDown++;
        if (ticksDown >= interval) {
            ticksDown = 0;
            // removed reflection call to private clickMouse
            mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }
}
