package ghostclient.module.modules.movement;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

/**
 * Move faster on ice ONLY (1-30 bps). Caps speed rather than multiplying every tick.
 */
public class IceSpeed extends Module {

    private final NumberValue speed = new NumberValue("Speed", "Max ice speed (blocks per second)", 8.0, 1.0, 15.0, 0.5);

    public IceSpeed() {
        super(Category.Movement, "IceSpeed", "Move faster on ice.");
        addSetting(speed);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.world == null) return;

        // Only activate on ice blocks — ignore any other surface
        BlockPos below = new BlockPos(mc.player.posX, mc.player.posY - 0.1, mc.player.posZ);
        Block block = mc.world.getBlockState(below).getBlock();
        boolean onIce = (block == Blocks.ICE || block == Blocks.PACKED_ICE || block == Blocks.FROSTED_ICE);
        if (!onIce) return;

        double maxBps = speed.getValue() / 20.0;
        double curSpeed = Math.sqrt(mc.player.motionX * mc.player.motionX + mc.player.motionZ * mc.player.motionZ);

        // Boost toward max speed but never exceed it (no exponential growth)
        if (curSpeed > 0 && curSpeed < maxBps) {
            double factor = maxBps / curSpeed;
            mc.player.motionX *= factor;
            mc.player.motionZ *= factor;
        } else if (curSpeed > maxBps) {
            double factor = maxBps / curSpeed;
            mc.player.motionX *= factor;
            mc.player.motionZ *= factor;
        }
    }
}
