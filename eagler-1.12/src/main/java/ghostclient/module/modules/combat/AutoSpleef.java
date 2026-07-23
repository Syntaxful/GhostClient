package ghostclient.module.modules.combat;

import ghostclient.event.EventHandler;
import ghostclient.event.TickEvent;
import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.NumberValue;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

/**
 * Breaks blocks under nearby players to city them.
 */
public class AutoSpleef extends Module {

    private final NumberValue range = new NumberValue("Range", "Spleef range", 5, 1, 6, 1);

    public AutoSpleef() {
        super(Category.Combat, "AutoSpleef", "Break blocks under nearby players.");
        addSetting(range);
    }

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.world == null) return;
        Entity target = findClosestPlayer();
        if (target == null) return;
        BlockPos under = new BlockPos(target.posX, target.posY - 1, target.posZ);
        if (!mc.world.isAirBlock(under)) {
            int pickSlot = findPickaxeSlot();
            if (pickSlot != -1) mc.player.inventory.currentItem = pickSlot;
            mc.playerController.clickBlock(under, EnumFacing.UP);
            mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }

    private Entity findClosestPlayer() {
        double best = range.getValue() + 1.0;
        Entity target = null;
        for (Entity entity : mc.world.playerEntities) {
            if (entity == mc.player) continue;
            double dist = mc.player.getDistanceToEntity(entity);
            if (dist < best) {
                best = dist;
                target = entity;
            }
        }
        return target;
    }

    private int findPickaxeSlot() {
        for (int i = 0; i < 9; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemPickaxe) return i;
        }
        return -1;
    }
}
