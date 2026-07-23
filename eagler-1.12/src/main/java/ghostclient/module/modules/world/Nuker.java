package ghostclient.module.modules.world;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.BooleanValue;
import ghostclient.setting.NumberValue;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

/**
 * Breaks blocks around the player. Includes a block-per-tick limiter.
 */
public class Nuker extends Module {

    private final NumberValue range = new NumberValue("Range", "Break radius", 4, 1, 6, 1);
    private final NumberValue limit = new NumberValue("Limit", "Max blocks to break per tick", 1, 1, 20, 1);
    private final BooleanValue ignoreTileEntities = new BooleanValue("IgnoreTileEntities", "Skip tile entities", true);

    public Nuker() {
        super(Category.World, "Nuker", "Break blocks around you.");
        addSetting(range);
        addSetting(limit);
        addSetting(ignoreTileEntities);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.world == null) return;
        int r = range.getInt();
        int max = limit.getInt();
        int broken = 0;
        BlockPos playerPos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
        for (int x = -r; x <= r; x++) {
            for (int y = -r; y <= r; y++) {
                for (int z = -r; z <= r; z++) {
                    BlockPos pos = playerPos.add(x, y, z);
                    if (!mc.world.isAirBlock(pos)) {
                        if (ignoreTileEntities.getValue() && mc.world.getTileEntity(pos) != null) continue;
                        mc.playerController.clickBlock(pos, EnumFacing.UP);
                        broken++;
                        if (broken >= max) return;
                    }
                }
            }
        }
    }
}
