package ghostclient.module.modules.player;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

/**
 * Switches to the best tool for the block being mined.
 */
public class AutoTool extends Module {

    public AutoTool() {
        super(Category.Player, "AutoTool", "Switch to the best tool for blocks.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        // removed due to private API
    }
}
