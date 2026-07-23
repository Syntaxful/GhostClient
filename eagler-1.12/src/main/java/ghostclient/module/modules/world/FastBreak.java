package ghostclient.module.modules.world;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

/**
 * Breaks the current block faster by repeatedly clicking it.
 */
public class FastBreak extends Module {

    public FastBreak() {
        super(Category.World, "FastBreak", "Break blocks faster.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.world == null) return;
        if (isHittingBlock()) {
            BlockPos pos = mc.objectMouseOver.getBlockPos();
            EnumFacing side = mc.objectMouseOver.sideHit;
            if (pos != null && side != null) {
                mc.playerController.clickBlock(pos, side);
            }
        }
    }

    private boolean isHittingBlock() {
        try {
            return mc.playerController.getIsHittingBlock();
        } catch (Exception e) {
            return false;
        }
    }
}
