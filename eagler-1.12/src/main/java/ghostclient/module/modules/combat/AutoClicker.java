package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;
import net.minecraft.util.EnumHand;

/**
 * Normal auto-clicker. Clicks left mouse while held.
 */
public class AutoClicker extends Module {

    private final NumberValue cps = new NumberValue("CPS", "Clicks per second", 12, 1, 30, 1);
    private int ticks = 0;

    public AutoClicker() {
        super(Category.Combat, "AutoClicker", "Hold left click to auto-click fast.");
        addSetting(cps);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || !mc.gameSettings.keyBindAttack.isKeyDown()) return;
        int interval = 20 / cps.getInt();
        if (interval < 1) interval = 1;
        ticks++;
        if (ticks >= interval) {
            ticks = 0;
            mc.player.swingArm(EnumHand.MAIN_HAND);
            // Trigger the click on whatever the crosshair is targeting.
            if (mc.objectMouseOver != null && mc.objectMouseOver.entityHit != null) {
                mc.playerController.attackEntity(mc.player, mc.objectMouseOver.entityHit);
            } else if (mc.objectMouseOver != null && mc.objectMouseOver.getBlockPos() != null) {
                mc.playerController.clickBlock(mc.objectMouseOver.getBlockPos(), mc.objectMouseOver.sideHit);
            }
        }
    }
}
