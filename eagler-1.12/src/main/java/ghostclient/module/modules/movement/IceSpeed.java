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
 * Move faster on ice.
 */
public class IceSpeed extends Module {

    private final NumberValue multiplier = new NumberValue("Multiplier", "Ice speed multiplier", 2.0, 1.0, 30.0, 0.1);

    public IceSpeed() {
        super(Category.Movement, "IceSpeed", "Move faster on ice.");
        addSetting(multiplier);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null) return;
        BlockPos below = new BlockPos(mc.player.posX, mc.player.posY - 0.1, mc.player.posZ);
        Block block = mc.world.getBlockState(below).getBlock();
        if (block == Blocks.ICE || block == Blocks.PACKED_ICE || block == Blocks.FROSTED_ICE) {
            mc.player.motionX *= multiplier.getValue();
            mc.player.motionZ *= multiplier.getValue();
        }
    }
}
