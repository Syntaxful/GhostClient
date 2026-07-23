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
        if (Math.abs(mc.player.motionX) < 0.01 && Math.abs(mc.player.motionZ) < 0.01) return;

        BlockPos ahead = new BlockPos(mc.player.posX + mc.player.motionX, mc.player.posY - 1, mc.player.posZ + mc.player.motionZ);
        BlockPos ahead2 = new BlockPos(mc.player.posX + mc.player.motionX * 2, mc.player.posY - 1, mc.player.posZ + mc.player.motionZ * 2);
        if (mc.world.isAirBlock(ahead) && mc.world.isAirBlock(ahead2)) {
            mc.player.motionX = 0;
            mc.player.motionZ = 0;
        }
    }
}
