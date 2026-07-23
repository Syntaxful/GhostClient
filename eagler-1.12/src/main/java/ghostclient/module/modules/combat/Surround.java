package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import net.minecraft.block.Block;
import net.minecraft.block.BlockObsidian;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

/**
 * Places obsidian around the player.
 */
public class Surround extends Module {

    public Surround() {
        super(Category.Combat, "Surround", "Place obsidian around your feet.");
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.world == null) return;
        BlockPos playerPos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
        for (EnumFacing facing : EnumFacing.HORIZONTALS) {
            BlockPos pos = playerPos.offset(facing);
            if (mc.world.isAirBlock(pos)) {
                int slot = findObsidianSlot();
                if (slot != -1) {
                    int old = mc.player.inventory.currentItem;
                    mc.player.inventory.currentItem = slot;
                    mc.playerController.processRightClickBlock(mc.player, mc.world, pos.down(), EnumFacing.UP, new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5), EnumHand.MAIN_HAND);
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                    mc.player.inventory.currentItem = old;
                }
            }
        }
    }

    private int findObsidianSlot() {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (!(stack == null || stack.func_190926_b()) && stack.getItem() instanceof ItemBlock) {
                Block block = ((ItemBlock) stack.getItem()).getBlock();
                if (block instanceof BlockObsidian) return i;
            }
        }
        return -1;
    }
}
