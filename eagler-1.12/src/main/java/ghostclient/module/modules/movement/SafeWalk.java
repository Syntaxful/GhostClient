package ghostclient.module.modules.movement;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import net.minecraft.util.math.BlockPos;

/**
 * Prevents walking off blocks.
 */
public class SafeWalk extends Module {

    public SafeWalk() {
        super(Category.Movement, "SafeWalk", "Stop before walking off edges.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || !mc.player.onGround) return;
        BlockPos ahead = new BlockPos(mc.player.posX + mc.player.motionX, mc.player.posY - 1, mc.player.posZ + mc.player.motionZ);
        if (mc.world.isAirBlock(ahead)) {
            mc.player.motionX = 0;
            mc.player.motionZ = 0;
        }
    }
}
