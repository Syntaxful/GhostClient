package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.RenderEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.util.RenderUtils;
import net.minecraft.block.BlockObsidian;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

/**
 * Highlights safe holes in the bedrock/obsidian layer.
 */
public class HoleESP extends Module {

    public HoleESP() {
        super(Category.Render, "HoleESP", "Highlight safe holes.");
    }

    @EventHandler
    public void onRender(RenderEvent.Post event) {
        if (mc.player == null || mc.world == null) return;
        BlockPos playerPos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
        for (int x = -5; x <= 5; x++) {
            for (int z = -5; z <= 5; z++) {
                BlockPos pos = playerPos.add(x, -1, z);
                if (mc.world.getBlockState(pos).getBlock() instanceof BlockObsidian) {
                    BlockPos hole = pos.add(0, 1, 0);
                    if (mc.world.isAirBlock(hole) && mc.world.isAirBlock(hole.up())) {
                        AxisAlignedBB box = new AxisAlignedBB(hole.getX(), hole.getY(), hole.getZ(), hole.getX() + 1, hole.getY() + 1, hole.getZ() + 1);
                        RenderUtils.drawBox(box, 0xFFFFFF00, false);
                    }
                }
            }
        }
    }
}
