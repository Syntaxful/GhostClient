package ghostclient.module.modules.world;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

/**
 * Breaks blocks around the player.
 */
public class Nuker extends Module {

    private final NumberValue range = new NumberValue("Range", "Break radius", 4, 1, 6, 1);
    private final ghostclient.setting.BooleanValue ignoreTileEntities = new ghostclient.setting.BooleanValue("IgnoreTileEntities", "Skip tile entities", true);

    public Nuker() {
        super(Category.World, "Nuker", "Break blocks around you.");
        addSetting(range);
        addSetting(ignoreTileEntities);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.world == null) return;
        int r = range.getInt();
        BlockPos playerPos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
        for (int x = -r; x <= r; x++) {
            for (int y = -r; y <= r; y++) {
                for (int z = -r; z <= r; z++) {
                    BlockPos pos = playerPos.add(x, y, z);
                    if (!mc.world.isAirBlock(pos)) {
                        if (ignoreTileEntities.getValue() && mc.world.getTileEntity(pos) != null) continue;
                        mc.playerController.clickBlock(pos, EnumFacing.UP);
                    }
                }
            }
        }
    }
}
