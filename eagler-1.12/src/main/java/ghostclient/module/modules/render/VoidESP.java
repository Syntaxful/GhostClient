package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.RenderEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.util.RenderUtils;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

/**
 * Highlights holes that lead to the void.
 */
public class VoidESP extends Module {

    public VoidESP() {
        super(Category.Render, "VoidESP", "Highlight holes to the void.");
    }

    @EventHandler
    public void onRender(RenderEvent.Post event) {
        if (mc.player == null || mc.world == null) return;
        BlockPos playerPos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
        for (int x = -10; x <= 10; x++) {
            for (int z = -10; z <= 10; z++) {
                BlockPos pos = playerPos.add(x, -5, z);
                if (mc.world.isAirBlock(pos) && pos.getY() < 0) {
                    AxisAlignedBB box = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
                    RenderUtils.drawBox(box, 0xFFFF0000, false);
                }
            }
        }
    }
}
