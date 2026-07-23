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
        if (mc.world == null || mc.getRenderManager() == null || mc.player == null) return;
        double maxSq = 64.0 * 64.0;
        for (net.minecraft.tileentity.TileEntity tile : mc.world.loadedTileEntityList) {
            int color = 0;
            if (tile instanceof TileEntityChest)          color = 0xFFFFFF00;
            else if (tile instanceof TileEntityEnderChest) color = 0xFFFF00FF;
            else if (tile instanceof TileEntityShulkerBox) color = 0xFF00FFFF;
            else continue;

            BlockPos pos = tile.getPos();
            if (mc.player.getDistanceSq(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) > maxSq) continue;
            AxisAlignedBB box = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
            RenderUtils.drawBox(box, color, true);
        }
    }
}
