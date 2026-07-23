package ghostclient.module.modules.player;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.util.ItemUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

/**
 * Switches to the best tool for the block being mined.
 * Only checks when the player is actually hitting a block.
 */
public class AutoTool extends Module {

    public AutoTool() {
        super(Category.Player, "AutoTool", "Switch to the best tool for blocks.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.world == null || mc.objectMouseOver == null) return;
        if (!isHittingBlock()) return;

        BlockPos pos = mc.objectMouseOver.getBlockPos();
        if (pos == null) return;
        IBlockState state = mc.world.getBlockState(pos);
        if (state == null) return;

        int bestSlot = -1;
        float bestSpeed = 1.0f;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (ItemUtil.isEmpty(stack)) continue;
            float speed = stack.getStrVsBlock(state);
            if (speed > bestSpeed) {
                bestSpeed = speed;
                bestSlot = i;
            }
        }
        if (bestSlot != -1 && mc.player.inventory.currentItem != bestSlot) {
            mc.player.inventory.currentItem = bestSlot;
        }
    }

    private boolean isHittingBlock() {
        try {
            return mc.playerController.getIsHittingBlock();
        } catch (Exception e) {
            return false;
        }
    }
}
