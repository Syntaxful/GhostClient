package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.BooleanValue;
import ghostclient.setting.NumberValue;
import net.minecraft.block.BlockBed;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

/**
 * Breaks beds around the player.
 * Has a per-click cooldown to prevent server-detectable spam in survival.
 */
public class BedAura extends Module {

    private final NumberValue range    = new NumberValue("Range",    "Bed break range",            5,  1, 7, 1);
    private final NumberValue cooldown = new NumberValue("Cooldown", "Ticks between breaks",       5,  1, 20, 1);
    private final BooleanValue strict  = new BooleanValue("Strict",  "Skip occupied beds",         true);
    private int ticksSinceBreak = 0;

    public BedAura() {
        super(Category.Combat, "BedAura", "Break nearby beds automatically.");
        addSetting(range);
        addSetting(cooldown);
        addSetting(strict);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.world == null) return;

        if (ticksSinceBreak > 0) {
            ticksSinceBreak--;
            return;
        }

        int r = range.getInt();
        double reach = range.getValue();
        double reachSq = reach * reach;
        BlockPos playerPos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);

        for (int x = -r; x <= r; x++) {
            for (int y = -r; y <= r; y++) {
                for (int z = -r; z <= r; z++) {
                    BlockPos pos = playerPos.add(x, y, z);
                    if (mc.player.getDistanceSq(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) > reachSq) continue;
                    net.minecraft.block.state.IBlockState state = mc.world.getBlockState(pos);
                    if (!(state.getBlock() instanceof BlockBed)) continue;
                    if (strict.getValue()) {
                        try {
                            if (state.getValue(BlockBed.OCCUPIED)) continue;
                        } catch (Exception ignored) {}
                    }
                    mc.playerController.clickBlock(pos, EnumFacing.UP);
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                    ticksSinceBreak = cooldown.getInt();
                    return;
                }
            }
        }
    }
}
