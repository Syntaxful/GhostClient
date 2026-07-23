package ghostclient.module.modules.world;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

/**
 * Automatically places blocks under you.
 */
public class Scaffold extends Module {

    public Scaffold() {
        super(Category.World, "Scaffold", "Auto places blocks under your feet.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.world == null) return;
        BlockPos pos = new BlockPos(mc.player.posX, mc.player.posY - 1, mc.player.posZ);
        if (mc.world.isAirBlock(pos)) {
            int blockSlot = findBlockSlot();
            if (blockSlot != -1) {
                int oldSlot = mc.player.inventory.currentItem;
                mc.player.inventory.currentItem = blockSlot;
                EnumFacing facing = EnumFacing.UP;
                Vec3d hitVec = new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
                mc.playerController.processRightClickBlock(mc.player, mc.world, pos, facing, hitVec, EnumHand.MAIN_HAND);
                mc.player.swingArm(EnumHand.MAIN_HAND);
                mc.player.inventory.currentItem = oldSlot;
            }
        }
    }

    private int findBlockSlot() {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (!(stack == null || stack.func_190926_b()) && stack.getItem() instanceof ItemBlock) {
                return i;
            }
        }
        return -1;
    }
}
