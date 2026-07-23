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
 * Breaks blocks around the player with an adjustable per-tick limit.
 */
public class Nuker extends Module {

    private final NumberValue range = new NumberValue("Range", "Break radius", 4, 1, 6, 1);
    private final NumberValue limit = new NumberValue("Limit", "Max blocks per tick", 1, 1, 20, 1);
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
        double reach = range.getValue();
        double reachSq = reach * reach;
        BlockPos playerPos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);

        for (int x = -r; x <= r; x++) {
            for (int y = -r; y <= r; y++) {
                for (int z = -r; z <= r; z++) {
                    BlockPos pos = playerPos.add(x, y, z);
                    if (mc.player.getDistanceSq(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) > reachSq) continue;
                    if (mc.world.isAirBlock(pos)) continue;
                    if (ignoreTileEntities.getValue() && mc.world.getTileEntity(pos) != null) continue;
                    mc.playerController.clickBlock(pos, EnumFacing.UP);
                    broken++;
                    if (broken >= max) return;
                }
            }
        }
    }
}
