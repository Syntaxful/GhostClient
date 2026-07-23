package ghostclient.module.modules.render;

import ghostclient.event.EventHandler;
import ghostclient.event.RenderEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.util.RenderUtils;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

/**
 * Highlights chests, ender chests, and shulker boxes.
 */
public class ChestESP extends Module {

    public ChestESP() {
        super(Category.Render, "ChestESP", "Highlights chests and storage blocks.");
    }

    @EventHandler
    public void onRender(RenderEvent.Post event) {
        if (mc.world == null || mc.getRenderManager() == null) return;
        for (net.minecraft.tileentity.TileEntity tile : mc.world.loadedTileEntityList) {
            int color = 0;
            if (tile instanceof TileEntityChest)          color = 0xFFFFFF00;
            else if (tile instanceof TileEntityEnderChest) color = 0xFFFF00FF;
            else if (tile instanceof TileEntityShulkerBox) color = 0xFF00FFFF;
            else continue;

            BlockPos pos = tile.getPos();
            AxisAlignedBB box = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
            RenderUtils.drawBox(box, color, true);
        }
    }
}
