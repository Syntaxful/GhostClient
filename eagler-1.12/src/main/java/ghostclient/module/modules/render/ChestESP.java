package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.RenderEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.util.RenderUtils;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

/**
 * Highlights storage containers.
 */
public class ChestESP extends Module {

    public ChestESP() {
        super(Category.Render, "ChestESP", "Highlights chests and ender chests.");
    }

    @EventHandler
    public void onRender(RenderEvent.Post event) {
        if (mc.world == null || mc.getRenderManager() == null) return;
        for (net.minecraft.tileentity.TileEntity tile : mc.world.loadedTileEntityList) {
            if (tile instanceof TileEntityChest || tile instanceof TileEntityEnderChest) {
                BlockPos pos = tile.getPos();
                AxisAlignedBB box = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
                int color = tile instanceof TileEntityEnderChest ? 0xFFFF00FF : 0xFFFFFF00;
                RenderUtils.drawBox(box, color, false);
            }
        }
    }
}
