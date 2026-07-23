package ghostclient.module.modules.movement;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import net.minecraft.util.math.BlockPos;

/**
 * Auto jumps at the edge of blocks.
 */
public class Parkour extends Module {

    public Parkour() {
        super(Category.Movement, "Parkour", "Auto jump at block edges.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || !mc.player.onGround) return;
        if (mc.gameSettings.keyBindForward.isKeyDown() && mc.player.moveForward > 0) {
            BlockPos ahead = new BlockPos(mc.player.posX + mc.player.motionX * 2, mc.player.posY - 1, mc.player.posZ + mc.player.motionZ * 2);
            if (mc.world.isAirBlock(ahead)) {
                mc.player.jump();
            }
        }
    }
}
