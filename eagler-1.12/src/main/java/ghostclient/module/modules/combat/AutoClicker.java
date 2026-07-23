package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

/**
 * Automatically clicks left click at a configurable CPS.
 */
public class AutoClicker extends Module {

    private final NumberValue cps = new NumberValue("CPS", "Clicks per second", 12, 1, 20, 1);
    private int ticksSinceClick = 0;

    public AutoClicker() {
        super(Category.Combat, "AutoClicker", "Auto left-click.");
        addSetting(cps);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.world == null) return;
        int interval = 20 / cps.getInt();
        if (interval < 1) interval = 1;

        ticksSinceClick++;
        if (ticksSinceClick >= interval) {
            ticksSinceClick = 0;
            click();
        }
    }

    private void click() {
        if (mc.pointedEntity != null) {
            mc.playerController.attackEntity(mc.player, mc.pointedEntity);
            mc.player.swingArm(EnumHand.MAIN_HAND);
        } else if (mc.objectMouseOver != null && mc.objectMouseOver.getBlockPos() != null) {
            BlockPos pos = mc.objectMouseOver.getBlockPos();
            EnumFacing side = mc.objectMouseOver.sideHit != null ? mc.objectMouseOver.sideHit : EnumFacing.UP;
            if (mc.playerController.clickBlock(pos, side)) {
                mc.player.swingArm(EnumHand.MAIN_HAND);
            }
        } else {
            mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }
}
